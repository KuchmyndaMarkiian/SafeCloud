package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Double2;

import java.io.Closeable;
import java.io.IOException;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.CustomTypes.Tuple;

/**
 * Created by Markiian Kuchmynda on 21.09.2017.
 */

public class SqliteManager implements Closeable {
    SQLiteDatabase sqLiteDatabase;
    SqlHelper sqlHelper;

    public SqliteManager(Context context) {
        sqlHelper = new SqlHelper(context);
        sqLiteDatabase = sqlHelper.getWritableDatabase();
    }

    void insert(String tableName, Tuple... cols) {
        ContentValues contentValues = formatValues(cols);
        sqLiteDatabase.insert(tableName, null, contentValues);
    }

    private ContentValues formatValues(Tuple... cols) {
        ContentValues contentValues = new ContentValues();
        for (Tuple tuple : cols) {
            if (tuple.getKey() instanceof String) {
                Object o = tuple.getValue();
                String key = (String) tuple.getKey();
                //region Converting
                if (o instanceof String)
                    contentValues.put(key, (String) tuple.getValue());
                else if (o instanceof Integer)
                    contentValues.put(key, (Integer) tuple.getValue());
                else if (o instanceof Double)
                    contentValues.put(key, (Double) tuple.getValue());
                else if (o instanceof Float)
                    contentValues.put(key, (Float) tuple.getValue());
                else if (o instanceof Boolean)
                    contentValues.put(key, (Boolean) tuple.getValue());
                else if (o instanceof byte[])
                    contentValues.put(key, (byte) tuple.getValue());
                else if (o instanceof Short)
                    contentValues.put(key, (Short) tuple.getValue());
                else if (o instanceof Long)
                    contentValues.put(key, (Long) tuple.getValue());
                else if (o instanceof Byte)
                    contentValues.put(key, (Byte) tuple.getValue());
//endregion
            }
        }
        return contentValues;
    }

    @Override
    public void close() throws IOException {
        sqLiteDatabase.close();
        sqlHelper.close();
    }

    class SqlHelper extends SQLiteOpenHelper {

        public SqlHelper(Context context) {
            super(context, "SafeCloudDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //TODO: Need to fix script`s  syntax
            db.execSQL(folderTable);
            db.execSQL(fileTable);
            db.execSQL(attributeTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public String getFolderTable() {
            return "Folders";
        }

        public String getFileTable() {
            return "Files";
        }

        public String getAttributeTableTable() {
            return "Attributes";
        }

        //region SQL Scripts
        private final String folderTable = "CREATE TABLE Folders (\n" +
                "    IdMain             INTEGER IDENTITY (1,1) NOT NULL,\n" +
                "    Id              NVARCHAR (128) NOT NULL,\n" +
                "    Header          NVARCHAR (256) NULL,\n" +
                "    Description     NVARCHAR (256) NULL,\n" +
                "    CreatedDate     DATETIME       NULL,\n" +
                "    Attribute_Id    INT            NOT NULL,\n" +
                "    ParentFolder_Id NVARCHAR (128) NULL,\n" +
                "    CONSTRAINT PK_dbo.Folders PRIMARY KEY CLUSTERED (Id ASC),\n" +
                "    CONSTRAINT FK_dbo.Folders_dbo.Attributes_Attribute_Id FOREIGN KEY (Attribute_Id) REFERENCES Attributes (Id) ON DELETE CASCADE,\n" +
                "    CONSTRAINT FK_dbo.Folders_dbo.Folders_ParentFolder_Id FOREIGN KEY (ParentFolder_Id) REFERENCES Folders (Id)\n" +
                ");\n" +
                "\n" +
                "\n" +
                "GO\n" +
                "CREATE UNIQUE NONCLUSTERED INDEX IX_Id\n" +
                "    ON Folders(Id ASC);\n" +
                "\n" +
                "\n" +
                "GO\n" +
                "CREATE NONCLUSTERED INDEX IX_Attribute_Id\n" +
                "    ON Folders(Attribute_Id ASC);\n" +
                "\n" +
                "\n" +
                "GO\n" +
                "CREATE NONCLUSTERED INDEX IX_ParentFolder_Id\n" +
                "    ON Folders(ParentFolder_Id ASC);";

        private final String fileTable = "CREATE TABLE Files (\n" +
                "    IdMain             INTEGER IDENTITY (1,1) NOT NULL,\n" +
                "    Id              NVARCHAR (128) NOT NULL,\n" +
                "    Header          NVARCHAR (256) NULL,\n" +
                "    Description     NVARCHAR (256) NULL,\n" +
                "    CreatedDate     DATETIME       NULL,\n" +
                "    Path            NVARCHAR (256) NULL,\n" +
                "    Geolocation     NVARCHAR (256) NULL,\n" +
                "    Attribute_Id    INT            NOT NULL,\n" +
                "    ParentFolder_Id NVARCHAR (128) NULL,\n" +
                "    CONSTRAINT PK_dbo.Files PRIMARY KEY CLUSTERED (Id ASC),\n" +
                "    CONSTRAINT FK_dbo.Files_dbo.Attributes_Attribute_Id FOREIGN KEY (Attribute_Id) REFERENCES Attributes (Id) ON DELETE CASCADE,\n" +
                "    CONSTRAINT FK_dbo.Files_dbo.Folders_ParentFolder_Id FOREIGN KEY (ParentFolder_Id) REFERENCES Folders (Id)\n" +
                ");\n" +
                "\n" +
                "\n" +
                "GO\n" +
                "CREATE UNIQUE NONCLUSTERED INDEX IX_Id\n" +
                "    ON Files(Id ASC);\n" +
                "\n" +
                "\n" +
                "GO\n" +
                "CREATE NONCLUSTERED INDEX IX_Attribute_Id\n" +
                "    ON Files(Attribute_Id ASC);\n" +
                "\n" +
                "\n" +
                "GO\n" +
                "CREATE NONCLUSTERED INDEX IX_ParentFolder_Id\n" +
                "    ON Files(ParentFolder_Id ASC);\n" +
                "\n";
        private final String attributeTable = "CREATE TABLE Attributes (\n" +
                "    IdMain             INTEGER IDENTITY (1,1) NOT NULL,\n" +
                "    Id              INT NOT NULL,\n" +
                "    HasPublicAccess BIT NOT NULL,\n" +
                "    CONSTRAINT PK_dbo.Attributes PRIMARY KEY CLUSTERED (Id ASC)\n" +
                ");\n" +
                "\n";
        //endregion
    }
}
