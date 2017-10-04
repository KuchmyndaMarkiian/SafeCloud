package cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

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

    //region DML scripts
    public void insert(FileStructureBase base){
        if(base.getSize()==null)
        {
            Folder folder=new Folder();
            folder.setDateTime(base.getDateTime());
            folder.setDescription(base.getDescription());
            folder.setHasPublicAccess(base.isHasPublicAccess());
            folder.setId(base.getId());
            folder.setName(base.getName());
            folder.setParentId(base.getParentId());
            insert(folder);
        }
        else {
            File file=new File();
            file.setDateTime(base.getDateTime());
            file.setDescription(base.getDescription());
            file.setHasPublicAccess(base.isHasPublicAccess());
            file.setId(base.getId());
            file.setName(base.getName());
            file.setParentId(base.getParentId());
            insert(file);
        }
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
    private void insert(String tableName, Tuple... cols) {
        ContentValues contentValues = formatValues(cols);
        sqLiteDatabase.insert(tableName, null, contentValues);
    }
    //todo: need to duplicate scripts for columns
    public void update(FileStructureBase base){
        if(base.getSize()==null)
        {
            Folder folder=new Folder();
            folder.setDateTime(base.getDateTime());
            folder.setDescription(base.getDescription());
            folder.setHasPublicAccess(base.isHasPublicAccess());
            folder.setId(base.getId());
            folder.setName(base.getName());
            folder.setParentId(base.getParentId());
            update(folder);
        }
        else {
            File file=new File();
            file.setDateTime(base.getDateTime());
            file.setDescription(base.getDescription());
            file.setHasPublicAccess(base.isHasPublicAccess());
            file.setId(base.getId());
            file.setName(base.getName());
            file.setParentId(base.getParentId());
            update(file);
        }
    }
    public void update(Folder folder) {
        if (!exists(folder)) return;
        update(SqlData.FolderTable,
                new Tuple(SqlData.COLUMN_ID, folder.getId()),
                new Tuple(SqlData.COLUMN_HEADER, folder.getName()),
                new Tuple(SqlData.COLUMN_DESCRIPTION, folder.getDescription()),
                new Tuple(SqlData.COLUMN_CREATED_DATE, folder.getDateTime()),
                new Tuple(SqlData.COLUMN_PUBLIC_ACCESS, folder.isHasPublicAccess()),
                new Tuple(SqlData.COLUMN_PARENT_ID, folder.getParentId()));
    }
    public void update(File file) {
        if (!exists(file)) return;
        update(SqlData.FileTable,
                new Tuple(SqlData.COLUMN_ID, file.getId()),
                new Tuple(SqlData.COLUMN_HEADER,file.getName()),
                new Tuple(SqlData.COLUMN_DESCRIPTION,file.getDescription()),
                new Tuple(SqlData.COLUMN_CREATED_DATE,file.getDateTime()),
                new Tuple(SqlData.COLUMN_PUBLIC_ACCESS,file.isHasPublicAccess()),
                new Tuple(SqlData.COLUMN_PARENT_ID,file.getParentId()),
                new Tuple(SqlData.COLUMN_GEOLOCATION,file.getGeolocation()));
    }
    private void update(String table, Tuple...cols){
        ContentValues contentValues= formatValues(cols);
        String id=null;
        for (Tuple col : cols) {
            if(col.getKey().equals(SqlData.COLUMN_ID))
                id= (String) col.getValue();
        }
        if(id!=null)
            Log.i("Updating SQL", String.valueOf(sqLiteDatabase.update(table,contentValues,String.format(" %s = ?", SqlData.COLUMN_ID),new String[]{ id})));
    }

    public void delete(FileStructureBase base){
        String table= base instanceof Folder?SqlData.FolderTable:SqlData.FileTable;
        sqLiteDatabase.delete(table,String.format("%s = ?", SqlData.COLUMN_ID), new String[]{base.getId()});
    }
    //endregion
    //region SELECTion scripts
    public Cursor select(String tableName) {
        return sqLiteDatabase.query(tableName, null, null, null, null, null, null);
    }
    public Cursor select(String tableName,String where,String... selectionArgs) {
        return sqLiteDatabase.query(tableName, null, where, selectionArgs, null, null, null);
    }
    public boolean exists(FileStructureBase structureBase) {
        String table= structureBase.getSize()==null?SqlData.FolderTable:SqlData.FileTable;
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(String.format("select count(*) from %s where Id='%s'",table, structureBase.getId()));
        return (int) sqLiteStatement.simpleQueryForLong() > 0;
    }
    public int count(String table, String id){
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(String.format("select count(*) from %s where ParentId=%s",table, id.equals("NULL")?id:String.format("'%s'")));
        return (int) sqLiteStatement.simpleQueryForLong();
    }
    //endregion

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
