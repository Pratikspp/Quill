import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QuillProductScraper {
  public static void main(String[] args) {
    String url = "https://www.quill.com/hanging-file-folders/cbl/4378.html";

    try {
      Document doc = Jsoup.connect(url).get();
      Elements products = doc.select(".js-prod-tile");

      FileWriter csvWriter = new FileWriter("quill_products.csv");
      csvWriter.append("Product Name,Product Price,Item Number,Model Number,Product Category,Product Description\n");

      for (Element product : products) {
        String name = product.select(".prod-title").text();
        String price = product.select(".prod-pricing").text().replace(",", "");
        String itemNumber = product.attr("data-item-number");
        String modelNumber = product.attr("data-model-number");
        String category = product.attr("data-category");
        String description = product.select(".prod-desc").text();

        csvWriter.append(String.join(",", name, price, itemNumber, modelNumber, category, description)).append("\n");
      }

      csvWriter.flush();
      csvWriter.close();

      System.out.println("Successfully scraped and saved product data to quill_products.csv");

    } catch (IOException e) {
      System.out.println("An error occurred while scraping the page: " + e.getMessage());
    }
  }
}
