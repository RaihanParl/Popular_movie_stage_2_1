package com.bidjidev.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JumpPage extends AppCompatActivity {
    @BindView(R.id.pageNow)
    TextView txtPage;
    @BindView(R.id.edPage)
    EditText edPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_page);
        ButterKnife.bind(this);
        txtPage.setText(MainActivity.page + "");

    }

    public void oJump(View view) {
        String sdatPage = edPage.getText().toString();
        if (sdatPage.equals("")) {
            Toast.makeText(this, "page is blank", Toast.LENGTH_SHORT).show();
        } else {
            int idatPage = Integer.parseInt(sdatPage);
            if (idatPage >= 1001) {
                Toast.makeText(this, "page must be less than or equal to 1000", Toast.LENGTH_SHORT).show();
            } else {
                MainActivity.page = idatPage;
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }
}
