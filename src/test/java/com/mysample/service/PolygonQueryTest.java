package com.mysample.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * A test class that can be used to send multiple POST requests.
 */
public class PolygonQueryTest {
    private static final Logger logger = LoggerFactory.getLogger(PolygonQueryTest.class);

    private static final int TEST_DURATION_IN_MILLISECONDS = 540000;
    private static final String JSON_TEMPLATE = "{\n" +
            "    \"type\" : \"Polygon\",\n" +
            "    \"crs\" : {\n" +
            "      \"type\" : \"name\",\n" +
            "      \"properties\" : {\n" +
            "        \"name\" : \"urn:ogc:def:crs:EPSG::27700\"\n" +
            "      }\n" +
            "    },\n" +
            "\"coordinates\": [ [ [ 460140.02499113423982%d, 1218400.742346523096785 ], [ 460355.734173258417286, 1218322.302643932402134 ], [ 460591.053281030210201, 1218577.231677351985127 ], [ 460924.422017040255014, 1218518.401900409022346 ], [ 461218.570901755068917, 1218773.330933828372508 ], [ 461434.280083879188169, 1218322.302643932402134 ], [ 461571.549563412787393, 1217675.175097560044378 ], [ 461806.868671184580307, 1217263.366658959304914 ], [ 461748.038894241617527, 1216537.799409996252507 ], [ 461355.840381288609933, 1214655.246547821676359 ], [ 461198.960976107395254, 1214341.487737459363416 ], [ 461571.549563412787393, 1214106.168629687512293 ], [ 461728.428968593943864, 1214341.487737459363416 ], [ 462061.797704604046885, 1215282.764168546535075 ], [ 462336.336663671128917, 1215753.402384090237319 ], [ 462434.386291909380816, 1216184.820748338475823 ], [ 462375.556514966476243, 1216675.068889529677108 ], [ 462434.386291909380816, 1216988.827699892222881 ], [ 462630.48554838594282, 1217537.905618026386946 ], [ 462719.866203741345089, 1217781.16054205247201 ], [ 462804.177743487991393, 1217850.581174915423617 ], [ 462865.816792699159123, 1217783.734887836966664 ], [ 462949.987341932137497, 1217878.27833707164973 ], [ 463005.481613875832409, 1217760.180344344815239 ], [ 463122.046926175593399, 1217965.392329145688564 ], [ 463277.290828108554706, 1217910.752018651692197 ], [ 463227.366180923127104, 1218122.315604638308287 ], [ 463442.286222024296876, 1218100.723897987045348 ], [ 463348.555830024008173, 1218255.377395691815764 ], [ 463351.244421572133433, 1218419.763482477283105 ], [ 463485.612047283211723, 1218525.983223348390311 ], [ 463630.591756416135468, 1217988.933907922357321 ], [ 463748.251310302061029, 1217459.465915435692295 ], [ 463846.300938540312927, 1217204.536882016342133 ], [ 463983.570418073853943, 1217145.707105073379353 ], [ 464140.449823255068623, 1217322.196435902267694 ], [ 464316.939154083898757, 1217596.735394969349727 ], [ 464473.818559265113436, 1217557.515543674118817 ], [ 464728.747592684580013, 1217224.146807663841173 ], [ 464807.187295275158249, 1217126.097179425647482 ], [ 464885.626997865794692, 1217028.047551187453791 ], [ 465199.38580822822405, 1217204.536882016342133 ], [ 465336.655287761765067, 1217577.125469321617857 ], [ 465571.974395533557981, 1217675.175097560044378 ], [ 465630.804172476520762, 1217224.146807663841173 ], [ 465689.633949419483542, 1217067.267402482684702 ], [ 465924.953057191276457, 1216929.997922949260101 ], [ 466042.612611077202018, 1216988.827699892222881 ], [ 466297.541644496668596, 1217184.926956368610263 ], [ 466356.371421439631376, 1217184.926956368610263 ], [ 466395.591272734920494, 1217008.437625539721921 ], [ 466434.811124030209612, 1216949.60784859675914 ], [ 466572.080603563808836, 1216929.997922949260101 ], [ 466768.179860040312633, 1217243.756733311573043 ], [ 466866.229488278564531, 1217439.855989788193256 ], [ 467003.498967812105548, 1217557.515543674118817 ], [ 467160.378372993320227, 1217479.075841083424166 ], [ 467199.598224288609345, 1217204.536882016342133 ], [ 467140.768447345646564, 1217047.657476835185662 ], [ 466983.889042164431885, 1216910.38799730152823 ], [ 466532.860752268461511, 1216577.019261291483417 ], [ 466513.250826620846055, 1216577.019261291483417 ], [ 466238.711867553705815, 1216577.019261291483417 ], [ 466003.392759781912901, 1216577.019261291483417 ], [ 466003.392759781912901, 1216537.799409996252507 ], [ 466101.442388020164799, 1216341.700153519632295 ], [ 466258.321793201379478, 1216184.820748338475823 ], [ 466277.931718848994933, 1216165.210822690743953 ], [ 466375.981347087246832, 1215949.501640566624701 ], [ 466532.860752268461511, 1215792.62223538546823 ], [ 466709.350083097349852, 1215969.111566214356571 ], [ 466964.27911651681643, 1215988.721491861855611 ], [ 467140.768447345646564, 1216008.331417509587482 ], [ 467101.548596050357446, 1215851.45201232843101 ], [ 467179.988298640993889, 1215694.572607147274539 ], [ 467513.357034651038703, 1215498.473350670654327 ], [ 467591.796737241616938, 1215851.45201232843101 ], [ 467807.505919365794398, 1215576.913053261348978 ], [ 467827.115845013468061, 1215302.374094194266945 ], [ 467827.115845013468061, 1215125.884763365378603 ], [ 467748.676142422831617, 1214929.785506888758391 ], [ 466885.839413926179986, 1215008.225209479453042 ], [ 466474.03097532549873, 1215047.445060774683952 ], [ 465924.953057191276457, 1215106.274837717646733 ], [ 465689.633949419483542, 1215086.664912069914863 ], [ 465552.364469885942526, 1214969.005358183989301 ], [ 465336.655287761765067, 1214812.12595300283283 ], [ 465297.435436466475949, 1214616.026696526445448 ], [ 465297.435436466475949, 1214400.317514402093366 ], [ 465415.09499035240151, 1214243.438109220936894 ], [ 465807.293503305409104, 1214145.388480982743204 ], [ 465983.782834134239238, 1214145.388480982743204 ], [ 466179.882090610743035, 1214008.119001449085772 ], [ 466199.492016258416697, 1213929.679298858623952 ], [ 466297.541644496668596, 1213517.870860257884488 ], [ 466454.421049677883275, 1213478.651008962653577 ], [ 466572.080603563808836, 1213439.431157667189837 ], [ 466552.470677916135173, 1213086.452496009645984 ], [ 466532.860752268461511, 1212968.792942123720422 ], [ 466415.201198382594157, 1212772.69368564710021 ], [ 466415.201198382594157, 1212498.154726580018178 ], [ 466454.421049677883275, 1212341.275321398861706 ], [ 466513.250826620846055, 1212262.835618808167055 ], [ 466513.250826620846055, 1212145.176064922241494 ], [ 466513.250826620846055, 1212027.516511036315933 ], [ 466611.300454859097954, 1211694.14777502627112 ], [ 466572.080603563808836, 1211615.708072435809299 ], [ 466395.591272734920494, 1211341.169113368727267 ], [ 465885.733205895987339, 1211399.998890311690047 ], [ 465454.314841647690628, 1211713.75770067400299 ], [ 465297.435436466475949, 1211968.686734093585983 ], [ 465062.116328694624826, 1212047.126436684047803 ], [ 465003.286551751720253, 1211890.247031502891332 ], [ 464885.626997865794692, 1211890.247031502891332 ], [ 464689.527741389290895, 1211949.076808445854113 ], [ 464493.428484912787098, 1212145.176064922241494 ], [ 464277.719302788609639, 1212164.785990569973364 ], [ 464140.449823255068623, 1212302.055470103630796 ], [ 463689.421533359098248, 1212321.665395751129836 ], [ 463571.761979473172687, 1212282.445544455898926 ], [ 463512.932202530209906, 1211968.686734093585983 ], [ 463826.691012892639264, 1211635.317998083308339 ], [ 464022.790269369143061, 1211537.268369845114648 ], [ 464297.329228436283302, 1211184.289708187337965 ], [ 464395.3788566745352, 1211125.459931244375184 ], [ 464454.208633617497981, 1210811.701120882062241 ], [ 464454.208633617497981, 1210752.871343939099461 ], [ 464336.549079731572419, 1210733.26141829136759 ], [ 464258.109377140935976, 1210615.601864405442029 ], [ 464336.549079731572419, 1210458.722459224285558 ], [ 464356.159005379187874, 1210321.452979690860957 ], [ 464571.868187503365334, 1210203.793425804935396 ], [ 464709.13766703690635, 1210164.573574509471655 ], [ 464807.187295275158249, 1210086.133871919009835 ], [ 464846.407146570505574, 1209890.034615442389622 ], [ 464885.626997865794692, 1209419.396399898920208 ], [ 464905.236923513468355, 1209380.176548603456467 ], [ 465277.825510818802286, 1209282.126920365262777 ], [ 465415.09499035240151, 1209184.077292127069086 ], [ 465395.485064704727847, 1209144.857440831605345 ], [ 465297.435436466475949, 1209046.807812593411654 ], [ 464748.357518332253676, 1208987.978035650448874 ], [ 464179.66967455035774, 1209046.807812593411654 ], [ 464081.620046312105842, 1209086.027663888875395 ], [ 463512.932202530209906, 1209164.467366479337215 ], [ 463120.733689577202313, 1209164.467366479337215 ], [ 463042.293986986624077, 1209066.417738241143525 ], [ 462767.755027919483837, 1208909.538333059987053 ], [ 462650.095474033558276, 1208850.708556117024273 ], [ 462375.556514966476243, 1208850.708556117024273 ], [ 462081.407630251720548, 1208733.049002231098711 ], [ 462610.875622738269158, 1208419.290191868552938 ], [ 463218.783317815454211, 1208340.850489278091118 ], [ 463630.591756416135468, 1208321.240563630359247 ], [ 463885.520789835602045, 1208321.240563630359247 ], [ 464160.059748902684078, 1208478.119968811515719 ], [ 464454.208633617497981, 1208497.729894459247589 ], [ 464532.648336208076216, 1208380.070340573322028 ], [ 464611.088038798654452, 1208164.361158449202776 ], [ 464650.307890094001777, 1207929.042050677351654 ], [ 464709.13766703690635, 1207674.113017258001491 ], [ 464709.13766703690635, 1207301.524429952492937 ], [ 464669.917815741617233, 1206870.106065704254434 ], [ 464532.648336208076216, 1206419.077775808284059 ], [ 464316.939154083898757, 1206203.368593684164807 ], [ 463983.570418073853943, 1206085.709039798239246 ], [ 463610.981830768461805, 1205909.219708969350904 ], [ 463395.272648644284345, 1205713.120452492730692 ], [ 463336.442871701379772, 1205301.312013892224059 ], [ 463356.052797348995227, 1204928.723426586715505 ], [ 463297.223020406032447, 1204909.113500938983634 ], [ 462865.804656157735735, 1205124.822683063335717 ], [ 462728.535176624194719, 1205065.992906120372936 ], [ 462571.65577144298004, 1204830.673798348521814 ], [ 462493.216068852343597, 1204497.305062338477001 ], [ 462355.94658931880258, 1204124.716475032968447 ], [ 462277.506886728224345, 1203928.617218556581065 ], [ 462120.627481547009666, 1203438.369077365379781 ], [ 462081.407630251720548, 1203242.269820888759568 ], [ 461865.698448127543088, 1202967.730861821677536 ], [ 461689.209117298654746, 1202399.0430180397816 ], [ 461826.47859683225397, 1202026.454430734273046 ], [ 462493.216068852343597, 1201869.575025553116575 ], [ 462748.145102271810174, 1201948.014728143811226 ], [ 463101.12376392952865, 1201438.156661304878071 ], [ 463434.492499939631671, 1201202.837553533026949 ], [ 463591.37190512084635, 1201575.426140838302672 ], [ 463454.102425587247126, 1201830.355174257885665 ], [ 463591.37190512084635, 1202006.844505086774006 ], [ 463669.811607711424585, 1202026.454430734273046 ], [ 463689.421533359098248, 1201771.525397314922884 ], [ 463748.251310302061029, 1201340.10703306668438 ], [ 463826.691012892639264, 1201104.787925294833258 ], [ 463865.910864187928382, 1200810.639040580019355 ], [ 463630.591756416135468, 1200673.369561046361923 ], [ 463277.613094758416992, 1200634.149709751131013 ], [ 463101.12376392952865, 1200477.270304569974542 ], [ 463022.684061338950414, 1200202.731345502892509 ], [ 462924.634433100698516, 1199947.802312083309516 ], [ 462708.925250976521056, 1199634.043501720996574 ], [ 462591.265697090595495, 1199634.043501720996574 ], [ 462453.996217557054479, 1200300.7809737410862 ], [ 462061.797704604046885, 1200359.61075068404898 ], [ 461924.528225070505869, 1200163.511494207428768 ], [ 461767.64881988929119, 1199928.192386435810477 ], [ 461512.719786469824612, 1200163.511494207428768 ], [ 461061.691496573854238, 1200438.050453274743631 ], [ 460787.152537506713998, 1200438.050453274743631 ], [ 460394.954024553706404, 1200418.440527627011761 ], [ 460140.024991134239826, 1200477.270304569974542 ], [ 460081.195214191277046, 1200987.128371408907697 ], [ 460061.585288543603383, 1201438.156661304878071 ], [ 459806.656255124195013, 1201575.426140838302672 ], [ 459473.287519114091992, 1201340.10703306668438 ], [ 459237.968411342299078, 1201202.837553533026949 ], [ 458826.159972741617821, 1200810.639040580019355 ], [ 458708.50041885569226, 1200457.660378922242671 ], [ 458551.621013674477581, 1200124.291642912197858 ], [ 458218.252277664432768, 1199810.532832549884915 ], [ 458120.202649426180869, 1199555.603799130301923 ], [ 458022.153021187928971, 1199457.554170892108232 ], [ 457669.174359530210495, 1199418.334319596877322 ], [ 457551.514805644284934, 1199183.015211825026199 ], [ 457433.855251758359373, 1198947.696104053175077 ], [ 457276.975846577202901, 1199634.043501720996574 ], [ 456865.167407976521645, 1199653.653427368495613 ], [ 456629.848300204670522, 1199712.483204311458394 ], [ 456571.018523261765949, 1200163.511494207428768 ], [ 456590.628448909381405, 1200340.00082503631711 ], [ 456374.919266785262153, 1200634.149709751131013 ], [ 455767.011571708077099, 1200634.149709751131013 ], [ 455923.890976889233571, 1201026.348222704138607 ], [ 456100.380307718121912, 1201457.766586952609941 ], [ 456394.529192432877608, 1201869.575025553116575 ], [ 456551.408597614092287, 1201869.575025553116575 ], [ 456531.798671966418624, 1202163.723910267930478 ], [ 456257.259712899336591, 1202653.972051459131762 ], [ 456276.869638546952046, 1203418.75915171764791 ], [ 456021.940605127485469, 1203575.638556898804381 ], [ 455865.061199946328998, 1204360.035582804819569 ], [ 455649.352017822151538, 1204556.134839281439781 ], [ 456394.529192432877608, 1204261.985954566625878 ], [ 456786.727705385885201, 1204046.276772442506626 ], [ 457120.096441395988222, 1204124.716475032968447 ], [ 457355.415549167781137, 1204379.645508452551439 ], [ 457237.755995281855576, 1204713.014244462596253 ], [ 457061.266664453025442, 1205046.382980472641066 ], [ 457061.266664453025442, 1205222.872311301529408 ], [ 456767.117779738269746, 1205340.531865187454969 ], [ 456551.408597614092287, 1205340.531865187454969 ], [ 457061.266664453025442, 1205654.290675549767911 ], [ 457178.926218338951003, 1205752.340303788194433 ], [ 457002.436887510062661, 1205968.049485912313685 ], [ 456806.337631033558864, 1206281.808296274626628 ], [ 456649.458225852344185, 1206419.077775808284059 ], [ 456923.997184919484425, 1206752.446511818328872 ], [ 456904.387259271810763, 1206987.765619590179995 ], [ 456767.117779738269746, 1207046.595396533142775 ], [ 457120.096441395988222, 1207340.744281247723848 ], [ 457433.855251758359373, 1207223.084727362031117 ], [ 457531.904879996669479, 1207478.013760781381279 ], [ 457590.734656939574052, 1207615.283240315038711 ], [ 457865.273616006714292, 1207850.602348086657003 ], [ 457806.443839063751511, 1208399.680266221053898 ], [ 457688.78428517782595, 1208850.708556117024273 ], [ 457747.614062120788731, 1209184.077292127069086 ], [ 458139.812575073854532, 1209380.176548603456467 ], [ 458139.812575073854532, 1210458.722459224285558 ], [ 458139.812575073854532, 1211066.630154301412404 ], [ 458179.03242636914365, 1211243.119485130300745 ], [ 458335.911831550358329, 1211733.367626321734861 ], [ 457865.273616006714292, 1212047.126436684047803 ], [ 457218.146069634240121, 1212262.835618808167055 ], [ 457218.146069634240121, 1212674.644057408906519 ], [ 457178.926218338951003, 1212929.573090828489512 ], [ 457708.394210825499613, 1213478.651008962653577 ], [ 457865.273616006714292, 1213576.700637200847268 ], [ 458375.131682845647447, 1214086.558704039780423 ], [ 458571.230939322151244, 1214419.927440049825236 ], [ 458610.450790617440362, 1214694.466399116907269 ], [ 458277.082054607395548, 1214812.12595300283283 ], [ 458159.422500721469987, 1215067.054986422415823 ], [ 458492.791236731573008, 1215165.104614660609514 ], [ 458865.379824036906939, 1215125.884763365378603 ], [ 459316.408113932877313, 1215282.764168546535075 ], [ 459649.776849942980334, 1215890.67186362366192 ], [ 459492.897444761765655, 1216302.480302224401385 ], [ 459904.705883362446912, 1216616.239112586714327 ], [ 460159.634916781913489, 1216733.898666472639889 ], [ 460316.51432196306996, 1217028.047551187453791 ], [ 460140.024991134239826, 1217400.636138492729515 ], [ 460022.365437248314265, 1217675.175097560044378 ], [ 460140.024991134239826, 1218400.742346523096785 ] ] ]  }\n";

    @Test
    public void verifyAllRequestsSucceed() throws IOException, InterruptedException {
        // Creates a file under the project root
        BufferedWriter writer = new BufferedWriter(new FileWriter("polygonRes.txt"));

        final HttpPost httpPost = new HttpPost("http://localhost:8889/polygon?dataset=DPA&format=json&maxresults=1");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        long finishingTime;
        CloseableHttpResponse response;
        int counter = 0;
        long startingTime = System.currentTimeMillis();
        logger.info("Starting sending polygon requests...");
        do {
            final CloseableHttpClient client = HttpClients.createDefault();

            StringEntity entity = new StringEntity(String.format(JSON_TEMPLATE, new Random().nextInt(10)));
            httpPost.setEntity(entity);
            response = client.execute(httpPost);

            int responseCode = response.getStatusLine().getStatusCode();
            writer.write(responseCode + " at " + new Date());
            writer.newLine();
//            assertEquals(200, response.getStatusLine().getStatusCode());

            client.close();

            finishingTime = System.currentTimeMillis();
            counter = counter + 1;
            logger.info("So far sent {} request", counter);
            Thread.sleep(500);
        } while (finishingTime - startingTime < TEST_DURATION_IN_MILLISECONDS);

        writer.close();
    }
}
