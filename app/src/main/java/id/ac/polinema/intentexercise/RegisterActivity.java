package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText et_fullname, et_email, et_password,et_confpassword, et_homepage, et_about;
    private Button buttonOk;
    private ImageView img_profile;
    private boolean change_img = false;
    private Uri imgUri = null;
    private Bitmap bitmap;
    private CircleImageView avatar, changeAvatar;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_fullname = findViewById(R.id.text_fullname);
        et_email = findViewById(R.id.text_email);
        et_password = findViewById(R.id.text_password);
        et_confpassword = findViewById(R.id.text_confirm_password);
        et_homepage = findViewById(R.id.text_homepage);
        et_about = findViewById(R.id.text_about);

        buttonOk = findViewById(R.id.button_ok);
        img_profile = findViewById(R.id.imageView);

        avatar = findViewById(R.id.image_profile);

        img_profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = et_fullname.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String confirmpassword = et_confpassword.getText().toString();
                String homepage = et_homepage.getText().toString();
                String about = et_about.getText().toString();

                Intent move = new Intent(RegisterActivity.this, ProfileActivity.class);

                if(!change_img){
                    Toast.makeText(RegisterActivity.this, "Image must be changed", Toast.LENGTH_LONG).show();
                }else if(fullname.isEmpty()){
                    et_fullname.setError("Name can't be emptied!");
                }else if(email.isEmpty()){
                    et_email.setError("Email can't be emptied!");
                }else if(password.isEmpty()){
                    et_password.setError("Password can't be emptied!");
                }else if(confirmpassword.isEmpty()) {
                    et_confpassword.setError("Confirm Password can't be emptied!");
                }else if(homepage.isEmpty()) {
                    et_confpassword.setError("Homepage can't be emptied!");
                }else if(about.isEmpty()) {
                    et_about.setError("About can't be emptied!");
                }else if (!password.equals(confirmpassword)) {
                    Toast.makeText(RegisterActivity.this, "Password don't match", Toast.LENGTH_SHORT).show();
                } else {
                    String image = imgUri.toString();
                    move.putExtra("KEY_IMG", image);
                    move.putExtra("KEY_NAME", fullname);
                    move.putExtra("KEY_EMAIL", email);
                    move.putExtra("KEY_PASS", password);
                    move.putExtra("KEY_CONFPASS", confirmpassword);
                    move.putExtra("KEY_HOME", homepage);
                    move.putExtra("KEY_ABOUT", about);
                    startActivity(move);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancel input image", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if (requestCode == GALLERY_REQUEST_CODE){
                if (data != null){
                    try{
                        change_img = true;
                        imgUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                        avatar.setImageBitmap(bitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }

}