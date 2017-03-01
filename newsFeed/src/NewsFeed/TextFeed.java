package NewsFeed;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TextFeed {

    private Text t;

    public Node bloom(double screenX, double screenY) {
        Group g = new Group();

        this.t = new Text();
        this.t.setText(readRSS());
        this.t.setFill(Color.RED);
        this.t.setFont(Font.font(null, FontWeight.BOLD, 60));
        this.t.setX(screenX / 2);
        this.t.setY(screenY / 12);

        TranslateTransition transTransition = TranslateTransitionBuilder.create()
                .duration(new Duration(55000))
                .node(this.t)
                .toX(-27000)
                .interpolator(Interpolator.LINEAR)
                .cycleCount(Timeline.INDEFINITE)
                .build();

        g.setCache(true);
        g.setEffect(new Bloom());
        g.getChildren().add(t);
        g.setTranslateX(350);
        transTransition.play();
        
        return g;
    }

    public void refresh() {
        this.t.setText(readRSS());
    }

    private String readRSS() {
        String news = "";

        try {
            //RSS URL
            URL url = new URL("http://feeds.bbci.co.uk/news/rss.xml?edition=int");
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpcon));
            List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();

            int i = 0;
            while (itEntries.hasNext() && i < 10) {
                SyndEntry entry = (SyndEntry) itEntries.next();
                news = news + "         " + entry.getTitle();
                i++;
            }
            httpcon.disconnect();
        } catch (IOException | IllegalArgumentException | FeedException e) {
            System.out.println("Exception: " + e);
        }

        return news;
    }
}
