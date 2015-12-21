package com.sys1yagi.core.entity.swipe;

import com.google.gson.Gson;
import com.sys1yagi.core.testtool.TestAssetsUtils;
import com.sys1yagi.swipe.core.entity.swipe.SwipeDocument;

import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hjson.Stringify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
public class SwipeTest {

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
        assertThat(swipe.getDimension())
                .hasLength(2);
        assertThat(swipe.getDimension()[0]).isEqualTo(1280);
        assertThat(swipe.getDimension()[1]).isEqualTo(0);
        assertThat(swipe.getPaging()).isEqualTo("vertical");
        assertThat(swipe.getOrientation()).isEqualTo("landscape");

        assertThat(swipe.getScenes().has("*")).isTrue();
        assertThat(swipe.getScenes().getAsJsonObject("*").get("bc").getAsString()).isEqualTo("#ddf");
        assertThat(swipe.getScenes().getAsJsonObject("demo").get("bc").getAsString()).isEqualTo("#fff");

        //TODO Named Element
        com.google.gson.JsonObject elements = swipe.getElements();
        assertThat(elements).isNotNull();


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
