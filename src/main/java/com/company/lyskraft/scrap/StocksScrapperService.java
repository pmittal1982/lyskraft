package com.company.lyskraft.scrap;

import com.company.lyskraft.entity.ShanghaiStockExchangeData;
import com.company.lyskraft.repository.ShanghaiStockExchangeDataRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class StocksScrapperService {
    private final ShanghaiStockExchangeDataRepository shanghaiStockExchangeDataRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Profile("production")
    @Scheduled(cron = "0 0 9-15 * * MON-FRI", zone = "Asia/Shanghai")
    public void scrapIndex() throws Exception {
        scrapPage("https://api.scrapfly.io/scrape?key=scp-live-f128126e84794a34867d8180083ec16c&url=https%3A%2F%2Fwww.shfe.com.cn%2Fen%2Fstatements%2Fdelaymarket_rb_en.html&retry=false&tags=player%2Cproject%3Adefault&timeout=15000");
        logger.info("IMP: Scrapped the data for : rb at " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        scrapPage("https://api.scrapfly.io/scrape?key=scp-live-f128126e84794a34867d8180083ec16c&url=https%3A%2F%2Fwww.shfe.com.cn%2Fen%2Fstatements%2Fdelaymarket_wr_en.html&retry=false&tags=player%2Cproject%3Adefault&timeout=15000");
        logger.info("IMP: Scrapped the data for : wr at " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        scrapPage("https://api.scrapfly.io/scrape?key=scp-live-f128126e84794a34867d8180083ec16c&url=https%3A%2F%2Fwww.shfe.com.cn%2Fen%2Fstatements%2Fdelaymarket_hc_en.html&retry=false&tags=player%2Cproject%3Adefault&timeout=15000");
        logger.info("IMP: Scrapped the data for : hc at " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        scrapPage("https://api.scrapfly.io/scrape?key=scp-live-f128126e84794a34867d8180083ec16c&url=https%3A%2F%2Fwww.shfe.com.cn%2Fen%2Fstatements%2Fdelaymarket_ss_en.html&retry=false&tags=player%2Cproject%3Adefault&timeout=15000");
        logger.info("IMP: Scrapped the data for : ss at " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

        //Delete the older than 30 days scrap.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date previousMonth = cal.getTime();
        logger.info("Deleting older than one month data");
        shanghaiStockExchangeDataRepository.deleteByCreatedDateBefore(previousMonth);
    }

    public void scrapPage(String pageUrl) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(pageUrl)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String responseStr = response.body().string();
        JsonObject json = JsonParser.parseString(responseStr).getAsJsonObject();
        String result = json.getAsJsonObject("result").get("content").getAsString();
        Elements rows = Jsoup.parse(result).select("tr");
        ShanghaiStockExchangeData shanghaiStockExchangeData;
        for (Element row : rows) {
            Elements columns = row.select("td[title=Contract], td[title=Last], td[title=Chg], td[title=Volume]");
            shanghaiStockExchangeData = new ShanghaiStockExchangeData();
            for (Element column : columns) {
                if (column.select("td[title=Contract]").size() > 0 && !column.text().isBlank()) {
                    shanghaiStockExchangeData.setName(column.text());
                } else if (column.select("td[title=Last]").size() > 0 && !column.text().isBlank()) {
                    shanghaiStockExchangeData.setPrice(Long.parseLong(column.text()));
                } else if (column.select("td[title=Chg]").size() > 0 && !column.text().isBlank()) {
                    shanghaiStockExchangeData.setChange(Integer.parseInt(column.text()));
                } else if (column.select("td[title=Volume]").size() > 0 && !column.text().isBlank()) {
                    shanghaiStockExchangeData.setVolume(Integer.parseInt(column.text()));
                }
            }
            if (shanghaiStockExchangeData.getName() != null && !shanghaiStockExchangeData.getName().isBlank()) {
                shanghaiStockExchangeDataRepository.save(shanghaiStockExchangeData);
            }
        }
    }
}