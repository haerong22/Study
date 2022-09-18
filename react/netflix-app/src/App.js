import requests from "./api/requests";
import "./App.css";
import Banner from "./components/Banner";
import Footer from "./components/Footer";
import Nav from "./components/Nav";
import Row from "./components/Row";

const App = () => {
  return (
    <div className="app">
      <Nav />
      <Banner />
      <Row
        title="NETFLIX ORIGINALS"
        id="NO"
        fetchUrl={requests.fetchNetflixOriginals}
        isLargeRow
      />

      <Row title="TRANDING NOW" id="TN" fetchUrl={requests.fetchTrending} />
      <Row title="TOP RATED" id="TR" fetchUrl={requests.fetchTopRated} />
      <Row
        title="ACTION MOVIES"
        id="AM"
        fetchUrl={requests.fetchActionMovies}
      />
      <Row
        title="COMEDY MOVIES"
        id="=CM"
        fetchUrl={requests.fetchComedyMovies}
      />

      <Footer />
    </div>
  );
};

export default App;
