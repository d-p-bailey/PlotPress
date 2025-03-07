package muse.writers;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "writersblock.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE story_shapes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");
        db.execSQL("CREATE TABLE narrative_types (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");
        db.execSQL("CREATE TABLE themes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");
        db.execSQL("CREATE TABLE settings (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");
        db.execSQL("CREATE TABLE characters (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, occupation TEXT, description TEXT, wants TEXT, needs TEXT, fears TEXT, goals TEXT, secrets TEXT);");
        db.execSQL("CREATE TABLE user_entries (id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT, value TEXT);");

        // Insert sample data
        db.execSQL("INSERT INTO story_shapes (name) VALUES ('Man in Hole'), ('Boy Meets Girl'), ('Cinderella');");
        db.execSQL("INSERT INTO narrative_types (name) VALUES ('First-person'), ('Third-person limited'), ('Third-person omniscient');");
        db.execSQL("INSERT INTO themes (name) VALUES ('Revenge'), ('Love conquers all'), ('Betrayal');");
        db.execSQL("INSERT INTO settings (name) VALUES ('Medieval Castle'), ('Dystopian Future'), ('Small Town');");
        db.execSQL("INSERT INTO characters (name, occupation, description, wants, needs, fears, goals, secrets) VALUES " +
                "('Alice', 'Detective', 'Sharp-witted investigator', 'Solve the case', 'Trust others', 'Being wrong', 'Justice', 'Has a dark past'), " +
                "('Bob', 'Bounty Hunter', 'Tough and relentless', 'Find his target', 'Forgiveness', 'Failure', 'Redemption', 'Has a twin brother');");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS story_shapes");
        db.execSQL("DROP TABLE IF EXISTS narrative_types");
        db.execSQL("DROP TABLE IF EXISTS themes");
        db.execSQL("DROP TABLE IF EXISTS settings");
        db.execSQL("DROP TABLE IF EXISTS characters");
        db.execSQL("DROP TABLE IF EXISTS user_entries");
        onCreate(db);
    }

    public String getRandomEntry(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM " + tableName + " ORDER BY RANDOM() LIMIT 1", null);

        String result = "[No data available]";
        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }
        cursor.close();
        return result;
    }

}

