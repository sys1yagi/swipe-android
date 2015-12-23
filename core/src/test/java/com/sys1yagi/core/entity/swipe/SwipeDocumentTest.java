package com.sys1yagi.core.entity.swipe;

import com.google.gson.Gson;
import com.sys1yagi.core.testtool.TestAssetsUtils;
import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument;

import org.assertj.core.data.Index;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hjson.Stringify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
public class SwipeDocumentTest {

    Gson gson;

    @Before
    public void setUp() {
        gson = new Gson();
    }

    @Test
    public void decodeFromJson() throws Exception {
        String jsonString = TestAssetsUtils.loadFromAssets("swipe.swipe");
        assertThat(jsonString).isNotEmpty();

        JsonObject jsonObject = JsonValue.readHjson(jsonString).asObject();
        assertThat(jsonObject).isNotNull();

        SwipeDocument swipe = gson.fromJson(jsonObject.toString(Stringify.FORMATTED), SwipeDocument.class);
        assertThat(swipe).isNotNull();
        assertThat(swipe.getBc()).isEqualTo(0);
        assertThat(swipe.getTitle()).isEqualTo("Swipe");
        assertThat(swipe.getDimension()).hasSize(2)
                .contains(1280, Index.atIndex(0))
                .contains(0, Index.atIndex(1));

        assertThat(swipe.getPaging()).isEqualTo("vertical");
        assertThat(swipe.getOrientation()).isEqualTo(SwipeDocument.Orientation.LANDSCAPE);

        assertThat(swipe.getScenes().getAsJsonObject("*").get("bc").getAsString()).isEqualTo("#ddf");
        assertThat(swipe.getScenes().getAsJsonObject("demo").get("bc").getAsString()).isEqualTo("#fff");

        //TODO Named Element Assertion
        com.google.gson.JsonObject elements = swipe.getElements();
        assertThat(elements).isNotNull();

        assertThat(elements.get("body").getAsJsonObject().get("x").getAsString()).isEqualTo("center");
        assertThat(elements.get("body").getAsJsonObject().get("w").getAsString()).isEqualTo("66.7%");
        assertThat(elements.get("code").getAsJsonObject().get("x").getAsString()).isEqualTo("4%");
        assertThat(elements.get("code").getAsJsonObject().get("y").getAsString()).isEqualTo("4%");
        assertThat(elements.get("code").getAsJsonObject().get("w").getAsString()).isEqualTo("44%");
        assertThat(elements.get("code").getAsJsonObject().get("h").getAsString()).isEqualTo("92%");

        assertThat(swipe.getPaths()).isNull();

        //TODO markdown
        assertThat(swipe.getMarkdown()).isNotNull();

        assertThat(swipe.getVoice()).isNull();

        //TODO pages
        assertThat(swipe.getPages()).hasSize(10);

        assertThat(swipe.getResources()).isNull();
        assertThat(swipe.isViewState()).isFalse();

    }
}
