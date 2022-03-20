import React, { useState, useRef } from 'react'
import ChatBox from './ChatBox/ChatBox'
import Analytics from './Analytics/Analytics'
import { BrowserRouter, Routes, Route } from 'react-router-dom'

function App() {
  // const [messages, setMessages] = useState(['Welcome to LionChat!'])

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<ChatBox />} />
        <Route path="/analytics" element={<Analytics />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
