package muse.writers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class CustomStoryActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Spinner shapeSpinner, narrativeSpinner, themeSpinner, settingSpinner;
    private Button generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_story);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new DatabaseHelper(this);
        shapeSpinner = findViewById(R.id.spinner_shape);
        narrativeSpinner = findViewById(R.id.spinner_narrative);
        themeSpinner = findViewById(R.id.spinner_theme);
        settingSpinner = findViewById(R.id.spinner_setting);
        generateButton = findViewById(R.id.btn_generate);

        setupSpinner(shapeSpinner, "story_shapes");
        setupSpinner(narrativeSpinner, "narrative_types");
        setupSpinner(themeSpinner, "themes");
        setupSpinner(settingSpinner, "settings");

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCustomStory();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSpinner(Spinner spinner, String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM " + tableName, null);

        ArrayList<String> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            items.add(cursor.getString(0));
        }
        cursor.close();

        if (items.isEmpty()) {
            items.add("No options available");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void generateCustomStory() {
        String shape = shapeSpinner.getSelectedItem().toString();
        String narrative = narrativeSpinner.getSelectedItem().toString();
        String theme = themeSpinner.getSelectedItem().toString();
        String setting = settingSpinner.getSelectedItem().toString();

        Intent intent = new Intent(CustomStoryActivity.this, CustomStoryOutputActivity.class);
        intent.putExtra("shape", shape);
        intent.putExtra("narrative", narrative);
        intent.putExtra("theme", theme);
        intent.putExtra("setting", setting);
        startActivity(intent);
    }
}
