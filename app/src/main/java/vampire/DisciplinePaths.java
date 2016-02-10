package vampire;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threemenstudio.vampire.R;

import java.util.ArrayList;
import java.util.List;

import data.Constants;
import data.Path;
import database.DbHelper;
import database.VtmDb;

public class DisciplinePaths extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_paths);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String disciplineId = extras.getString(Constants.EXTRA_DISCIPLINE);
        String discipline = extras.getString(Constants.EXTRA_TITLE);
        String official = extras.getString(Constants.EXTRA_OFFICIAL);

        getSupportActionBar().setTitle(discipline);

        TextView off = (TextView) findViewById(R.id.official_desc);
        off.setText(official);

        if(official == null){
            findViewById(R.id.ll_official).setVisibility(View.GONE);
        }

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        List<Path> paths = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            paths = vtmDb.getPathsOfDiscipline(db, Integer.parseInt(disciplineId));
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        for(int i = 0; i < paths.size(); i++){
            LinearLayout path = (LinearLayout) LinearLayout.inflate(context, R.layout.path, null);
            TextView title = (TextView) path.findViewById(R.id.title);
            title.setText(paths.get(i).getName());
            LinearLayout header = (LinearLayout) path.findViewById(R.id.header);
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            LinearLayout scrollView = (LinearLayout) findViewById(R.id.paths);
            scrollView.addView(path);
        }
    }

}
