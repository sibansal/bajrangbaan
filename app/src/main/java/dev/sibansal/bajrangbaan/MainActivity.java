package dev.sibansal.bajrangbaan;

import static java.lang.String.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textCanvas = initTextCanvas();
        initZoomControls(textCanvas);
        loadText(textCanvas);
    }

    private void setFontSize(float textSize)
    {
        TextView fontSizeView = findViewById(R.id.fontSize);
        fontSizeView.setText(format(Locale.US,"%.0f", textSize));
    }

    private TextView initTextCanvas()
    {
        TextView textCanvas = findViewById(R.id.textCanvas);
        textCanvas.setGravity(1);
        textCanvas.setPadding(10,0,10,0);
        setFontSize(textCanvas.getTextSize());
        return textCanvas;
    }

    private void initZoomControls(TextView textCanvas)
    {
        Button plus = findViewById(R.id.plus);
        Button minus = findViewById(R.id.minus);
        plus.setOnClickListener(view -> {
            float fontSize = textCanvas.getTextSize();
            textCanvas.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize + 5);
            setFontSize(fontSize);
        });
        minus.setOnClickListener(view -> {
            float fontSize = textCanvas.getTextSize();
            textCanvas.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize - 5);
            setFontSize(fontSize);
        });
    }

    private void loadText(TextView textCanvas)
    {
        try {
            InputStream inputStream = getAssets().open("bajrang_baan.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            reader.close();
            inputStream.close();
            textCanvas.setText(text.toString());
        } catch (IOException e) {
            textCanvas.setText(R.string.textErr);
            e.printStackTrace();
        }
    }
}