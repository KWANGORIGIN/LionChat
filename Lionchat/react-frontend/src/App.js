import React, { useState, useRef } from 'react'
import ChatBox from './ChatBox/ChatBox'
import Analytics from './Analytics/Analytics'
import { BrowserRouter, Routes, Route, useRoutes } from 'react-router-dom'

const MyRoutes = () => useRoutes([
  {path: "/", element: <ChatBox />},
  {path: "/chat", element: <ChatBox />},
  {path: "/administrative", element: <Analytics />},
])

function App() {
  // const [messages, setMessages] = useState(['Welcome to LionChat!'])

  return (
    <BrowserRouter>
      {
      /* <Routes>
         <Route path={["/", "/chat"]} element={<ChatBox />} />
         <Route path="/administrative" element={<Analytics />} />
       </Routes> */
       }
      <MyRoutes />
    </BrowserRouter>
  )
}

export default App
