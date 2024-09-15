package debugger.app.shclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SceneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.scenes_activity);

        ImageButton bAdd = (ImageButton) findViewById(R.id.bAddScene);


        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SceneActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.new_scene_activity, null);

                Spinner spinRole = customLayout.findViewById(R.id.spinner);
                String[] userRoles = {"датчик движения"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SceneActivity.this,
                    R.layout.spinner_item, userRoles);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinRole.setAdapter(adapter);

                Spinner spinRole2 = customLayout.findViewById(R.id.spinner2);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SceneActivity.this,
                    R.layout.spinner_item, userRoles);
                adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinRole2.setAdapter(adapter2);


                builder.setView(customLayout);
                builder.setPositiveButton("OK", (dialog, which) -> {

                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}
