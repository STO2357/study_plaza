import React from "react";
import DevTools from "./pages/DevTools";

function App() {
  return (
    <div className="App">
      <h1>메인 페이지</h1>
      {process.env.NODE_ENV === "development" && <DevTools />}
    </div>
  );
}

export default App;