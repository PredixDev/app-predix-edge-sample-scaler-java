package com.ge.predix.edge.sample;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * @author 212307911
 *
 */
public class SampleCallbackTest {
    //Prepare variables for testing
    private JSONObject fromBrokerOneTag = new JSONObject();
    private JSONObject oneTagNoChange = new JSONObject();
    private JSONObject oneTagYesChange = new JSONObject();
    private JSONObject fromBrokerTwoDifferentTags = new JSONObject();
    private JSONObject fromBrokerTwoSameTags = new JSONObject();
    private JSONObject twoTagsNoChange = new JSONObject();
    private JSONObject twoTagsOneChange = new JSONObject();
    private JSONObject twoTagsBothChange = new JSONObject();

    /**
     *
     */
    @Before
    //Build all of the JSON in timeseries format we need in our tests
    public void setUp(){
        //JSON object simulating OPC-UA data with one tag
        fromBrokerOneTag.put("messageId", "flex-pipe");
        //Construct tagged data
        JSONArray oneTagBody = new JSONArray();
        JSONObject oneTagMessage = new JSONObject();
        oneTagMessage.put("datapoints", new JSONArray());
        JSONArray oneTagDatapoints = new JSONArray();
        oneTagDatapoints.put(1537377630622L);
        oneTagDatapoints.put(80.0);
        oneTagDatapoints.put(3);
        oneTagMessage.getJSONArray("datapoints").put(oneTagDatapoints);
        oneTagMessage.put("name", "My.App.DOUBLE1");
        oneTagMessage.put("attributes", new JSONObject());
        oneTagMessage.getJSONObject("attributes").put("machine_type", "opcua");
        oneTagBody.put(oneTagMessage);
        fromBrokerOneTag.put("body", oneTagBody);


        //JSON object simulating OPC-UA data with two different tags
        fromBrokerTwoDifferentTags.put("messageId", "flex-pipe");
        //Construct the first tagged data point
        JSONArray twoDifferentTagsBody0 = new JSONArray();
        JSONObject twoDifferentTagsMessage0 = new JSONObject();
        twoDifferentTagsMessage0.put("datapoints", new JSONArray());
        JSONArray twoDifferentTagsDatapoints0 = new JSONArray();
        twoDifferentTagsDatapoints0.put(1537377630622L);
        twoDifferentTagsDatapoints0.put(80.0);
        twoDifferentTagsDatapoints0.put(3);
        twoDifferentTagsMessage0.getJSONArray("datapoints").put(twoDifferentTagsDatapoints0);
        twoDifferentTagsMessage0.put("name", "My.App.DOUBLE1");
        twoDifferentTagsMessage0.put("attributes", new JSONObject());
        twoDifferentTagsMessage0.getJSONObject("attributes").put("machine_type", "opcua");
        twoDifferentTagsBody0.put(twoDifferentTagsMessage0);

        //Construc the second tagged data point
        JSONObject twoDifferentTagsMessage1 = new JSONObject();
        twoDifferentTagsMessage1.put("datapoints", new JSONArray());
        JSONArray twoDifferentTagsDatapoints1 = new JSONArray();
        twoDifferentTagsDatapoints1.put(1537377630622L);
        twoDifferentTagsDatapoints1.put(112.64);
        twoDifferentTagsDatapoints1.put(3);
        twoDifferentTagsMessage1.getJSONArray("datapoints").put(twoDifferentTagsDatapoints1);
        twoDifferentTagsMessage1.put("name", "My.App.DOUBLE2");
        twoDifferentTagsMessage1.put("attributes", new JSONObject());
        twoDifferentTagsMessage1.getJSONObject("attributes").put("machine_type", "opcua");
        twoDifferentTagsBody0.put(twoDifferentTagsMessage1);

        fromBrokerTwoDifferentTags.put("body", twoDifferentTagsBody0);

        //JSON object simulating OPC-UA data with two of the same tags
        fromBrokerTwoSameTags.put("messageId", "flex-pipe");
        //Construct the first tagged data point
        JSONArray twoSameTagsBody0 = new JSONArray();
        JSONObject twoSameTagsMessage0 = new JSONObject();
        twoSameTagsMessage0.put("datapoints", new JSONArray());
        JSONArray twoSameTagsDatapoints0 = new JSONArray();
        twoSameTagsDatapoints0.put(1537377630622L);
        twoSameTagsDatapoints0.put(80.0);
        twoSameTagsDatapoints0.put(3);
        twoSameTagsMessage0.getJSONArray("datapoints").put(twoSameTagsDatapoints0);
        twoSameTagsMessage0.put("name", "My.App.DOUBLE1");
        twoSameTagsMessage0.put("attributes", new JSONObject());
        twoSameTagsMessage0.getJSONObject("attributes").put("machine_type", "opcua");
        twoSameTagsBody0.put(twoSameTagsMessage0);

        //Construct the second tagged data point
        JSONObject twoSameTagsMessage1 = new JSONObject();
        twoSameTagsMessage1.put("datapoints", new JSONArray());
        JSONArray twoSameTagsDatapoints1 = new JSONArray();
        twoSameTagsDatapoints1.put(1537377630622L);
        twoSameTagsDatapoints1.put(112.64);
        twoSameTagsDatapoints1.put(3);
        twoSameTagsMessage1.getJSONArray("datapoints").put(twoSameTagsDatapoints1);
        twoSameTagsMessage1.put("name", "My.App.DOUBLE1");
        twoSameTagsMessage1.put("attributes", new JSONObject());
        twoSameTagsMessage1.getJSONObject("attributes").put("machine_type", "opcua");
        twoSameTagsBody0.put(twoSameTagsMessage1);

        fromBrokerTwoSameTags.put("body", twoSameTagsBody0);


    }

    /**
     *
     */
    /*@Test
    public void testOneTagNoChange(){
        //Construct the expected JSON for our assertion
        oneTagNoChange.put("messageId", "flex-pipe");
        JSONArray body = new JSONArray();
        JSONObject message0 = new JSONObject();
        message0.put("datapoints", new JSONArray());
        JSONArray datapoints = new JSONArray();
        datapoints.put(1537377630622L);
        datapoints.put(80.0);
        datapoints.put(3);
        message0.getJSONArray("datapoints").put(datapoints);
        message0.put("name", "My.App.DOUBLE1");
        message0.put("attributes", new JSONObject());
        message0.getJSONObject("attributes").put("machine_type", "opcua");
        body.put(message0);
        oneTagNoChange.put("body", body);
        JSONObject actual = SampleCallback.scaleData(fromBrokerOneTag);
        assertEquals(oneTagNoChange.toString(), actual.toString());


    }*/

    /**
     *
     */
    @Test
    public void testOneTagYesChange(){
        //Construct the expected JSON for our assertion
        oneTagYesChange.put("messageId", "flex-pipe");
        JSONArray body = new JSONArray();
        JSONObject message0 = new JSONObject();
        message0.put("datapoints", new JSONArray());
        JSONArray datapoints = new JSONArray();
        datapoints.put(1537377630622L);
        datapoints.put(80000);
        datapoints.put(3);
        message0.getJSONArray("datapoints").put(datapoints);
        message0.put("name", "My.App.DOUBLE1");
        message0.put("attributes", new JSONObject());
        message0.getJSONObject("attributes").put("machine_type", "opcua");
        body.put(message0);
        oneTagYesChange.put("body", body);
        JSONObject actual = SampleCallback.scaleData(fromBrokerOneTag);
        assertEquals(oneTagYesChange.getJSONArray("body").getJSONObject(0).get("name") + ".scaled_x_1000", actual.getJSONArray("body").getJSONObject(0).get("name"));


    }

    /**
     *
     */
    /*@Test
    public void testTwoTagsNoChange(){
        //Construct the expected JSON for our assertion
        twoTagsNoChange.put("messageId", "flex-pipe");
        //Construct the first tagged data point
        JSONArray body1 = new JSONArray();
        JSONObject message1 = new JSONObject();
        message1.put("datapoints", new JSONArray());
        JSONArray datapoints1 = new JSONArray();
        datapoints1.put(1537377630622L);
        datapoints1.put(80.0);
        datapoints1.put(3);
        message1.getJSONArray("datapoints").put(datapoints1);
        message1.put("name", "My.App.DOUBLE1");
        message1.put("attributes", new JSONObject());
        message1.getJSONObject("attributes").put("machine_type", "opcua");
        body1.put(message1);

        //Construc the second tagged data point
        JSONObject message2 = new JSONObject();
        message2.put("datapoints", new JSONArray());
        JSONArray datapoints2 = new JSONArray();
        datapoints2.put(1537377630622L);
        datapoints2.put(112.64);
        datapoints2.put(3);
        message2.getJSONArray("datapoints").put(datapoints2);
        message2.put("name", "My.App.DOUBLE2");
        message2.put("attributes", new JSONObject());
        message2.getJSONObject("attributes").put("machine_type", "opcua");
        body1.put(message2);

        twoTagsNoChange.put("body", body1);

        JSONObject actual = SampleCallback.scaleData(fromBrokerTwoDifferentTags);
        assertEquals(twoTagsNoChange.toString(), actual.toString());
    }*/

    /**
     *
     */
    /*@Test
    public void testTwoTagsOneChange(){
        //Construct the expected JSON for our assertion
        twoTagsOneChange.put("messageId", "flex-pipe");
        //Construct the first tagged data point
        JSONArray body1 = new JSONArray();
        JSONObject message1 = new JSONObject();
        message1.put("datapoints", new JSONArray());
        JSONArray datapoints1 = new JSONArray();
        datapoints1.put(1537377630622L);
        datapoints1.put(80.0);
        datapoints1.put(3);
        message1.getJSONArray("datapoints").put(datapoints1);
        message1.put("name", "My.App.DOUBLE1");
        message1.put("attributes", new JSONObject());
        message1.getJSONObject("attributes").put("machine_type", "opcua");
        body1.put(message1);

        //Construc the second tagged data point, which we expect to be scaled by 1000 and have tag named changed
        JSONObject message2 = new JSONObject();
        message2.put("datapoints", new JSONArray());
        JSONArray datapoints2 = new JSONArray();
        datapoints2.put(1537377630622L);
        datapoints2.put(112640);
        datapoints2.put(3);
        message2.getJSONArray("datapoints").put(datapoints2);
        message2.put("name", "My.App.DOUBLE2");
        message2.put("attributes", new JSONObject());
        message2.getJSONObject("attributes").put("machine_type", "opcua");
        body1.put(message2);

        twoTagsOneChange.put("body", body1);

        JSONObject actual = SampleCallback.scaleData(fromBrokerTwoDifferentTags);
        assertEquals(twoTagsOneChange.toString(), actual.toString());

    }*/


    /**
     *
     */
    @Test
    public void testTwoTagsBothChange(){
        //Construct the expected JSON for our assertion
        twoTagsBothChange.put("messageId", "flex-pipe");
        //Construct the first tagged data point
        JSONArray body1 = new JSONArray();
        JSONObject message1 = new JSONObject();
        message1.put("datapoints", new JSONArray());
        JSONArray datapoints1 = new JSONArray();
        datapoints1.put(1537377630622L);
        datapoints1.put(80000);
        datapoints1.put(3);
        message1.getJSONArray("datapoints").put(datapoints1);
        message1.put("name", "My.App.DOUBLE1");
        message1.put("attributes", new JSONObject());
        message1.getJSONObject("attributes").put("machine_type", "opcua");
        body1.put(message1);

        //Construc the second tagged data point, which we expect to be scaled by 1000 and have tag named changed
        JSONObject message2 = new JSONObject();
        message2.put("datapoints", new JSONArray());
        JSONArray datapoints2 = new JSONArray();
        datapoints2.put(1537377630622L);
        datapoints2.put(112640);
        datapoints2.put(3);
        message2.getJSONArray("datapoints").put(datapoints2);
        message2.put("name", "My.App.DOUBLE1");
        message2.put("attributes", new JSONObject());
        message2.getJSONObject("attributes").put("machine_type", "opcua");
        body1.put(message2);

        twoTagsBothChange.put("body", body1);

        JSONObject actual = SampleCallback.scaleData(fromBrokerTwoSameTags);
        assertEquals(twoTagsBothChange.getJSONArray("body").getJSONObject(0).get("name") + ".scaled_x_1000", actual.getJSONArray("body").getJSONObject(0).get("name"));

    }



}
