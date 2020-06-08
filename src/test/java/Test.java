import com.sportmaster.configuration.BasicConfiguration;
import com.sportmaster.formatter.ResultFormatter;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        BasicConfiguration.class

})
public class Test {

    @Autowired
    private ResultFormatter resultFormatter;

    @org.junit.Test
    public void standartQuestTags() throws IOException {
        String metaInfoPath = "src/test/resources/meta_info1.json";
        final String s1 = resultFormatter.formatStr("src/test/resources/data1.json", metaInfoPath);
        Assert.assertEquals("<strong>Obama</strong> visited <strong>Facebook</strong> headquaters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a>",s1);

        final String s2 = resultFormatter.formatStr("src/test/resources/data2.json", metaInfoPath);
        Assert.assertEquals("<strong>Obama</strong> visited <strong>Facebook</strong> headquaters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a> word2 word3",s2);

        final String s3 = resultFormatter.formatStr("src/test/resources/data3.json", metaInfoPath);
        Assert.assertEquals("word1 <strong>Obama</strong> visited <strong>Facebook</strong> headquaters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a>",s3);

        final String s4 = resultFormatter.formatStr("src/test/resources/data4.json", metaInfoPath);
        Assert.assertEquals("word1 <strong>Obama</strong> visited <strong>Facebook</strong> headquaters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a> word2",s4);
    }

    @org.junit.Test
    public void customTags() throws IOException {
        String metaInfoPath = "src/test/resources/meta_info2.json";
        final String s1 = resultFormatter.formatStr("src/test/resources/data1_custom.json", metaInfoPath);
        Assert.assertEquals("<div>Obama</div> <custom>visited</custom> <div>Facebook</div> headquaters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\" class=\"twitter_class_name\">elversatile</a>",s1);

        final String s2 = resultFormatter.formatStr("src/test/resources/data2.json", metaInfoPath);
        Assert.assertEquals("<div>Obama</div> visited <div>Facebook</div> headquaters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\" class=\"twitter_class_name\">elversatile</a> word2 word3",s2);

        final String s3 = resultFormatter.formatStr("src/test/resources/data3.json", metaInfoPath);
        Assert.assertEquals("word1 <div>Obama</div> visited <div>Facebook</div> headquaters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\" class=\"twitter_class_name\">elversatile</a>",s3);

        final String s4 = resultFormatter.formatStr("src/test/resources/data4.json", metaInfoPath);
        Assert.assertEquals("word1 <div>Obama</div> visited <div>Facebook</div> headquaters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\" class=\"twitter_class_name\">elversatile</a> word2",s4);
    }
}
