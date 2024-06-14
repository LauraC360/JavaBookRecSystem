import axios from 'axios';
import cheerio from 'cheerio';

async function fetchGoodreadsImage(book) {
    try {
      const proxyUrl = 'https://anacorsproxy-6498d32363f3.herokuapp.com/';
      const response = await axios.get(proxyUrl + book.url, {
        headers: {
          'X-Requested-With': 'XMLHttpRequest'
        }
      });
      const $ = cheerio.load(response.data);
      const firstImageSrc = $('.ResponsiveImage[role="presentation"]').first().attr('src');
      return firstImageSrc;
    } catch (error) {
      console.error(error);
    }
}

export default fetchGoodreadsImage;