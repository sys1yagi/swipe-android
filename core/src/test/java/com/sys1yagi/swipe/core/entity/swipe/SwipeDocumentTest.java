package com.sys1yagi.swipe.core.entity.swipe;

import com.sys1yagi.swipe.core.testtool.TestAssetsUtils;
import com.sys1yagi.swipe.core.tool.json.JsonConverter;

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

    @Before
    public void setUp() {

    }

    @Test
    public void decodeFromJson() throws Exception {
        String jsonString = TestAssetsUtils.loadFromAssets("swipe.swipe");
        assertThat(jsonString).isNotEmpty();

        JsonObject jsonObject = JsonValue.readHjson(jsonString).asObject();
        assertThat(jsonObject).isNotNull();

        SwipeDocument swipe = JsonConverter.getInstance().fromJson(jsonObject.toString(Stringify.FORMATTED), SwipeDocument.class);
        assertThat(swipe).isNotNull();
        assertThat(swipe.getBc()).isEqualTo(0);
        assertThat(swipe.getTitle()).isEqualTo("Swipe");
        assertThat(swipe.getDimension()).hasSize(2)
                .contains(1280, Index.atIndex(0))
                .contains(0, Index.atIndex(1));

        assertThat(swipe.getPaging()).isEqualTo("vertical");
        assertThat(swipe.getOrientation()).isEqualTo(SwipeDocument.Orientation.LANDSCAPE);

        assertThat(swipe.getScenes().get("*").getBc()).isEqualTo("#ddf");
        assertThat(swipe.getScenes().get("demo").getBc()).isEqualTo("#fff");

        //TODO Named Element Assertion
        NamedElements elements = swipe.getElements();
        assertThat(elements).isNotNull();

        assertThat(elements.get("body")).isNotNull();
        assertThat(elements.get("body").getX()).isEqualTo("center");
        assertThat(elements.get("body").getW()).isEqualTo("66.7%");
        assertThat(elements.get("code").getX()).isEqualTo("4%");
        assertThat(elements.get("code").getY()).isEqualTo("4%");
        assertThat(elements.get("code").getW()).isEqualTo("44%");
        assertThat(elements.get("code").getH()).isEqualTo("92%");

        assertThat(swipe.getPaths()).isNull();


        assertThat(swipe.getMarkdown()).isNotNull();
        assertThat(swipe.getMarkdown().getStyles().size()).isEqualTo(7);
        assertThat(swipe.getMarkdown().getStyles().get("#").getColor()).isEqualTo("#616161");
        assertThat(swipe.getMarkdown().getStyles().get("#").getAlignment()).isEqualTo("center");
        assertThat(swipe.getMarkdown().getStyles().get("#").getFont().getSize()).isEqualTo(92);
        assertThat(swipe.getMarkdown().getStyles().get("#").getFont().getName()).isEqualTo("Helvetica-Bold");

        assertThat(swipe.getVoice()).isNull();

        //TODO pages
        assertThat(swipe.getPages()).hasSize(10);

        assertThat(swipe.getResources()).isNull();
        assertThat(swipe.isViewState()).isFalse();

    }
}
