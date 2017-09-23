package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.io.Closeable;
import java.io.IOException;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.CustomTypes.Tuple;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.SqlData;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud.File;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud.FileStructureBase;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud.Folder;

/**
 * Created by Markiian Kuchmynda on 21.09.2017.
 */

public class SqliteManager implements Closeable {
    private final SQLiteDatabase sqLiteDatabase;
    private final SqlHelper sqlHelper;

    public SqliteManager(Context context) {
        sqlHelper = new SqlHelper(context);
        sqLiteDatabase = sqlHelper.getWritableDatabase();
    }

    public void insert(Folder folder)
    {
        insert(SqlData.FolderTable,new Tuple(SqlData.COLUMN_ID, folder.getId()),
                new Tuple(SqlData.COLUMN_HEADER,folder.getName()),
                new Tuple(SqlData.COLUMN_DESCRIPTION,folder.getDescription()),
                new Tuple(SqlData.COLUMN_CREATED_DATE,folder.getDateTime()),
                new Tuple(SqlData.COLUMN_PUBLIC_ACCESS,folder.isHasPublicAccess()),
                new Tuple(SqlData.COLUMN_PARENT_ID,folder.getParentId()));
    }
    public void insert(File file)
    {
        insert(SqlData.FileTable,new Tuple(SqlData.COLUMN_ID, file.getId()),
                new Tuple(SqlData.COLUMN_HEADER,file.getName()),
                new Tuple(SqlData.COLUMN_DESCRIPTION,file.getDescription()),
                new Tuple(SqlData.COLUMN_CREATED_DATE,file.getDateTime()),
                new Tuple(SqlData.COLUMN_PUBLIC_ACCESS,file.isHasPublicAccess()),
                new Tuple(SqlData.COLUMN_PARENT_ID,file.getParentId()),
                new Tuple(SqlData.COLUMN_GEOLOCATION,file.getGeolocation()));
    }
    public int count(String table){
        SQLiteStatement sqLiteStatement= sqLiteDatabase.compileStatement("select count(*) from "+ table);
        return (int) sqLiteStatement.simpleQueryForLong();
    }

    private void insert(String tableName, Tuple... cols) {
        ContentValues contentValues = formatValues(cols);
        sqLiteDatabase.insert(tableName, null, contentValues);
    }
    public Cursor select(String tableName) {
        return sqLiteDatabase.query(tableName, null, null, null, null, null, null);
    }
    public Cursor select(String tableName,String where,String... selectionArgs) {
        return sqLiteDatabase.query(tableName, null, where, selectionArgs, null, null, null);
    }
    public boolean exists(FileStructureBase structureBase) {
        String table= structureBase instanceof Folder?SqlData.FolderTable:SqlData.FileTable;
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(String.format("select count(*) from %s where Id='%s'",table, structureBase.getId()));
        return (int) sqLiteStatement.simpleQueryForLong() > 0;
    }

    private static ContentValues formatValues(Tuple... cols) {
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
            db.execSQL(folderTable);
            db.execSQL(fileTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        //region SQL Scripts
        private final String folderTable = "CREATE TABLE Folders (\n" +
                "    Id              NVARCHAR (128) NOT NULL PRIMARY KEY,\n" +
                "    Header          NVARCHAR (256) NULL,\n" +
                "    Description     NVARCHAR (256) NULL,\n" +
                "    CreatedDate     DATETIME       NULL,\n" +
                "    HasPublicAccess    BIT            NOT NULL,\n" +
                "    ParentFolder_Id NVARCHAR (128) NULL,\n" +
                "    FOREIGN KEY(ParentFolder_Id) REFERENCES Folders(Id));\n";

        private final String fileTable = "CREATE TABLE Files (\n" +
                "    Id              NVARCHAR (128) NOT NULL PRIMARY KEY,\n" +
                "    Header          NVARCHAR (256) NULL,\n" +
                "    Description     NVARCHAR (256) NULL,\n" +
                "    CreatedDate     DATETIME       NULL,\n" +
                "    Geolocation     NVARCHAR (256) NULL,\n" +
                "    HasPublicAccess    BIT            NOT NULL,\n" +
                "    ParentFolder_Id NVARCHAR (128) NULL,\n" +
                "    FOREIGN KEY(ParentFolder_Id) REFERENCES Folders(Id));\n";

        //endregion
    }
}
