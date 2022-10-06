package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView img_profile;
    private TextView l_about, l_fullname, l_email, l_homepage;
    private Button btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        img_profile = findViewById(R.id.image_profile);
        l_about = findViewById(R.id.label_about);
        l_fullname = findViewById(R.id.label_fullname);
        l_email = findViewById(R.id.label_email);
        l_homepage = findViewById(R.id.label_homepage);
        btn_home = findViewById(R.id.button_homepage);

        String about = getIntent().getExtras().getString("KEY_ABOUT");
        String fullname = getIntent().getExtras().getString("KEY_NAME");
        String email = getIntent().getExtras().getString("KEY_EMAIL");
        final String homepage = getIntent().getExtras().getString("KEY_HOME");

        Uri uri = Uri.parse(getIntent().getExtras().getString("KEY_IMG"));
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            img_profile.setImageBitmap(bitmap);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to loading images!", Toast.LENGTH_SHORT).show();
        }

        l_about.setText(about);
        l_fullname.setText(fullname);
        l_email.setText(email);
        l_homepage.setText(homepage);

        btn_home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(homepage));
                startActivity(webIntent);
            }
        });

    }
}