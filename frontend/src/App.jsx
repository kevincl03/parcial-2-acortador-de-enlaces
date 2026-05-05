import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom'
import Home from './pages/Home'
import Links from './pages/Links'

function App() {
  return (
    <Router>
      <nav>
        <Link to="/">🏠 Home (Acortador)</Link>
        <Link to="/enlaces">🔗 Mis Enlaces</Link>
      </nav>
      
      <div className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/enlaces" element={<Links />} />
        </Routes>
      </div>
    </Router>
  )
}

export default App
