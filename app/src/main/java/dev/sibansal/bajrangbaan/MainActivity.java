package dev.sibansal.bajrangbaan;

import static java.lang.String.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_canvas);
        TextView textCanvas = initTextCanvas();
        hideChapterMenu();
        initChapterMenu();
        initZoomControls(textCanvas);
        loadBajrangBaan();
        initChapterButtons();
    }

    private void initChapterButtons()
    {
        Button bajrangBaan = findViewById(R.id.bajrangBaan);
        Button hanumanChalisa = findViewById(R.id.hanumanChalisa);
        Button hanumanAarti = findViewById(R.id.hanumanAarti);
        Button sankatMochan = findViewById(R.id.sankatMochan);

        bajrangBaan.setOnClickListener(view -> {
            loadBajrangBaan();
            hideChapterMenu();
        });

        hanumanChalisa.setOnClickListener(view -> {
            loadHanumanChalisa();
            hideChapterMenu();
        });

        hanumanAarti.setOnClickListener(view -> {
            loadHanumanAarti();
            hideChapterMenu();
        });

        sankatMochan.setOnClickListener(view -> {
            loadSankatMochan();
            hideChapterMenu();
        });
    }

    private void loadBajrangBaan()
    {
        TextView textCanvas = findViewById(R.id.textCanvas);
        String textFile="bajrang_baan.txt";
        setChapterTitle("बजरंग बाण");
        loadText(textCanvas,textFile);
    }

    private void loadHanumanChalisa()
    {
        TextView textCanvas = findViewById(R.id.textCanvas);
        String textFile="hanuman_chalisa.txt";
        setChapterTitle("हनुमान चालीसा");
        loadText(textCanvas,textFile);
    }
    private void loadHanumanAarti()
    {
        TextView textCanvas = findViewById(R.id.textCanvas);
        String textFile="aarati.txt";
        setChapterTitle("हनुमान आरती");
        loadText(textCanvas,textFile);
    }
    private void loadSankatMochan()
    {
        TextView textCanvas = findViewById(R.id.textCanvas);
        String textFile="sankat_mochan.txt";
        setChapterTitle("संकट मोचन");
        loadText(textCanvas,textFile);
    }

    private void setChapterTitle(String chapterTitle)
    {
        TextView title=findViewById(R.id.chapterTitle);
        title.setText(chapterTitle);
    }

    private void initChapterMenu()
    {
        Button chapterMenu = findViewById(R.id.chapterMenu);
        View chapterView=findViewById(R.id.topView);
        chapterMenu.setOnClickListener(view->
        {
            if (chapterView.getVisibility()==View.VISIBLE)
            {
                chapterView.setVisibility(View.GONE);
            }
            else {
                showChapterMenu();
            }
        });
    }
    private void showChapterMenu()
    {
        View chapterView=findViewById(R.id.topView);
        chapterView.setVisibility(View.VISIBLE);
    }

    private void hideChapterMenu()
    {
        View chapterView=findViewById(R.id.topView);
        chapterView.setVisibility(View.GONE);
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

    private void loadText(TextView textCanvas, String textFile)
    {
        try {
            InputStream inputStream = getAssets().open(textFile);
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