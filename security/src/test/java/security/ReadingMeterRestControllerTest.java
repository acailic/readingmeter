package security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.oauth2.common.util.JacksonJsonParser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.header.Header;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import readingmeter.*;
import readingmeter.repositories.AccountRepository;
import readingmeter.repositories.ConnectionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import readingmeter.repositories.ProfileRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ReadingMeterRestControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    private String userName = "ilke";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Account account;

    private List<Connection> connectionList = new ArrayList<>();
    private List<Profile> profileList = new ArrayList<>();

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    /*@Autowired
    private OAuthHelper authHelper;*/


    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build();
/*       this.accountRepository.deleteAll();
        this.profileRepository.deleteAll();
        this.connectionRepository.deleteAll();*/

        this.account = accountRepository.save(new Account(userName, "password"));
        Set<Fraction> fractions = new HashSet();
        fractions.add(new Fraction(Month.JAN, 0.1));
        fractions.add(new Fraction(Month.FEB, 0.1));
        fractions.add(new Fraction(Month.MAR, 0.1));
        fractions.add(new Fraction(Month.APR, 0.1));
        fractions.add(new Fraction(Month.MAY, 0.1));
        fractions.add(new Fraction(Month.JUN, 0.0));
        fractions.add(new Fraction(Month.JUL, 0.0));
        fractions.add(new Fraction(Month.AUG, 0.1));
        fractions.add(new Fraction(Month.SEP, 0.1));
        fractions.add(new Fraction(Month.OCT, 0.1));
        fractions.add(new Fraction(Month.NOV, 0.1));
        fractions.add(new Fraction(Month.DEC, 0.1));
        Profile profile1 = new Profile(account, "A", fractions, "http://profile.com/1/\"");
        this.profileList.add(this.profileRepository.save(profile1));

        Set<Fraction> fractionsNew = new HashSet();
        fractionsNew.add(new Fraction(Month.JAN, 0.2));
        fractionsNew.add(new Fraction(Month.FEB, 0.1));
        fractionsNew.add(new Fraction(Month.MAR, 0.1));
        fractionsNew.add(new Fraction(Month.APR, 0.1));
        fractionsNew.add(new Fraction(Month.MAY, 0.1));
        fractionsNew.add(new Fraction(Month.JUN, 0.0));
        fractionsNew.add(new Fraction(Month.JUL, 0.0));
        fractionsNew.add(new Fraction(Month.AUG, 0.1));
        fractionsNew.add(new Fraction(Month.SEP, 0.1));
        fractionsNew.add(new Fraction(Month.OCT, 0.1));
        fractionsNew.add(new Fraction(Month.NOV, 0.1));
        fractionsNew.add(new Fraction(Month.DEC, 0.0));
        Profile profile2 = new Profile(account, "B", fractionsNew, "http://profile.com/2/\"");
        this.profileList.add(this.profileRepository.save(profile2));
        Set<MeterReading> meterReadings = new HashSet();
        meterReadings.add(new MeterReading(Month.JAN, 1));
        meterReadings.add(new MeterReading(Month.FEB, 2));
        meterReadings.add(new MeterReading(Month.MAR, 3));
        meterReadings.add(new MeterReading(Month.APR, 4));
        meterReadings.add(new MeterReading(Month.MAY, 5));
        meterReadings.add(new MeterReading(Month.JUN, 6));
        meterReadings.add(new MeterReading(Month.JUL, 7));
        meterReadings.add(new MeterReading(Month.AUG, 8));
        meterReadings.add(new MeterReading(Month.SEP, 9));
        meterReadings.add(new MeterReading(Month.OCT, 10));
        meterReadings.add(new MeterReading(Month.NOV, 11));
        meterReadings.add(new MeterReading(Month.DEC, 12));
        Set<MeterReading> meterReadingsNew = new HashSet();
        meterReadingsNew.add(new MeterReading(Month.JAN, 1));
        meterReadingsNew.add(new MeterReading(Month.FEB, 2));
        meterReadingsNew.add(new MeterReading(Month.MAR, 3));
        meterReadingsNew.add(new MeterReading(Month.APR, 4));
        meterReadingsNew.add(new MeterReading(Month.MAY, 5));
        meterReadingsNew.add(new MeterReading(Month.JUN, 6));
        meterReadingsNew.add(new MeterReading(Month.JUL, 7));
        meterReadingsNew.add(new MeterReading(Month.AUG, 8));
        meterReadingsNew.add(new MeterReading(Month.SEP, 9));
        meterReadingsNew.add(new MeterReading(Month.OCT, 10));
        meterReadingsNew.add(new MeterReading(Month.NOV, 11));
        meterReadingsNew.add(new MeterReading(Month.DEC, 12));


        this.connectionList.add(connectionRepository.save(new Connection(//account,
                Math.abs(new Random().nextLong()),
                profile1, meterReadings, "http://connection.com/1/")));
        this.connectionList.add(connectionRepository.save(new Connection(//account,
                Math.abs(new Random().nextLong()),
                profile2, meterReadingsNew, "http://connection.com/1/")));

    }


    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/profiles1/")
                .content(this.json(new Profile()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }


    @Test
    public void readSingleProfile() throws Exception {
      //  RequestPostProcessor bearerToken = authHelper.addBearerToken("test", "ROLE_USER");

        mockMvc.perform(get("/profiles/"
                + this.profileList.get(0).getId()) //.with(bearerToken)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.profileList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.uri", is("http://profile.com/1/" + userName)));
    }

    @Test
    public void readConnections() throws Exception {
        //RequestPostProcessor bearerToken = authHelper.addBearerToken("test", "ROLE_USER");

        mockMvc.perform(get("/connections") //.with(bearerToken)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.connectionList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].uri", is("http://connection.com/1/" + userName)))
                .andExpect(jsonPath("$[1].id", is(this.connectionList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].uri", is("http://connection.com/2/" + userName)));
    }


    @Test
    public void createProfile() throws Exception {

        Set<Fraction> fractions = new HashSet();
        fractions.add(new Fraction(Month.JAN, 0.1));
        fractions.add(new Fraction(Month.FEB, 0.1));
        fractions.add(new Fraction(Month.MAR, 0.1));
        fractions.add(new Fraction(Month.APR, 0.1));
        fractions.add(new Fraction(Month.MAY, 0.1));
        fractions.add(new Fraction(Month.JUN, 0.0));
        fractions.add(new Fraction(Month.JUL, 0.0));
        fractions.add(new Fraction(Month.AUG, 0.1));
        fractions.add(new Fraction(Month.SEP, 0.1));
        fractions.add(new Fraction(Month.OCT, 0.1));
        fractions.add(new Fraction(Month.NOV, 0.1));
        fractions.add(new Fraction(Month.DEC, 0.1));
        String profileJson = json(new Profile(
                this.account,
                "A",
                fractions,
                "http://profile.com/A"));
        //RequestPostProcessor bearerToken = authHelper.addBearerToken("test", "ROLE_USER");

        this.mockMvc.perform(post("/profiles")
                //.with(bearerToken)

                .contentType(contentType)
                .content(profileJson))
                .andExpect(status().isCreated());
    }


    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
