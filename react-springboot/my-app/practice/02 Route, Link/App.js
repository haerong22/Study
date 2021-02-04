import { Route } from 'react-router-dom';
import Navigation from './components/Navigation';
import ListPage from './pages/ListPage';
import WritePage from './pages/WritePage';

function App() {
  return (
    <div>
      <Navigation />
      <ListPage />

      {/* <Route path="/" exact={true} component={ListPage}></Route>
      <Route path="/write" exact={true} component={WritePage}></Route> */}
    </div>
  );
}

export default App;
