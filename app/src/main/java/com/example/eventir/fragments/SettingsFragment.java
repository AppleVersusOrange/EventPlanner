package com.example.eventir.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eventir.BitmapScaler;
import com.example.eventir.R;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SettingsFragment extends Fragment {

    public static final String TAG = "fragmentSettings";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private CheckBox cbPublic;
    private EditText etZipcode;
    private ImageView ivNewPicture;
    private Button btnTakePicture;
    private Button btnConfirm;
    private File photoFile;
    private String photoFileName;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cbPublic = view.findViewById(R.id.cbPublic);
        etZipcode = view.findViewById(R.id.etZipcode);
        ivNewPicture = view.findViewById(R.id.ivNewPicture);
        btnTakePicture = view.findViewById(R.id.btnTakePicture);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        etZipcode.setText(ParseUser.getCurrentUser().get("ZIPcode").toString());
        //photoFile = ParseUser.getCurrentUser().getParseFile("profilePicture").getFile();
        Glide.with(getContext()).load(ParseUser.getCurrentUser().getParseFile("profilePicture").getUrl()).transform(new CropCircleTransformation()).into(ivNewPicture);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipcode = etZipcode.getText().toString();
                if(zipcode.isEmpty()){
                    Toast.makeText(getContext(), "ZIP code cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(zipcode.length() != 5){
                    Toast.makeText(getContext(), "ZIP code must be 5 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser user = ParseUser.getCurrentUser();
                user.put("ZIPcode", zipcode);
                if(photoFile != null) {
                    user.put("profilePicture", new ParseFile(photoFile));
                }
                if(cbPublic.isChecked()){
                    user.put("visibility", true);
                }
                else{
                    user.put("visibility", false);
                }
                user.saveInBackground(e -> {
                    if(e == null){
                        Toast.makeText(getContext(), "Save Successful!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });
    }
    private void launchCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.eventir.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    public File getPhotoFileUri (String fileName){
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // See code above
                Uri takenPhotoUri = Uri.fromFile(getPhotoFileUri(photoFileName));
// by this point we have the camera photo on disk
                Bitmap rawTakenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
// See BitmapScaler.java: https://gist.github.com/nesquena/3885707fd3773c09f1bb
                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, 5);
                // Configure byte output stream
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
// Compress the image further
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 5, bytes);
// Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
                File resizedFile = getPhotoFileUri(photoFileName + "_resized");
                try {
                    resizedFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(resizedFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
// Write the bytes of the bitmap to file
                try {
                    fos.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the taken image into a preview
                ivNewPicture.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
