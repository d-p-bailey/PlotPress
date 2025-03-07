package muse.writers;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RandomStoryActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView storyOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_story);

        // Set up the Toolbar as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new DatabaseHelper(this);
        storyOutput = findViewById(R.id.story_output);

        generateRandomStory();
    }

    private void generateRandomStory() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String shape = dbHelper.getRandomEntry("story_shapes");
        String narrative = dbHelper.getRandomEntry("narrative_types");
        String theme = dbHelper.getRandomEntry("themes");
        String setting = dbHelper.getRandomEntry("settings");

        String story = "Story Shape: " + shape + "\n" +
                "Narrative Type: " + narrative + "\n" +
                "Theme: " + theme + "\n" +
                "Setting: " + setting;

        storyOutput.setText(story);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
