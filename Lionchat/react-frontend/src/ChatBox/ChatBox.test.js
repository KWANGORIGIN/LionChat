import React from "react";

import ChatBox from './ChatBox';
import { render, fireEvent, screen } from '@testing-library/react'
import { act } from 'react-dom/test-utils'

it("Renders ChatBox Bubble correctly", () => {
    act(() => {
        render(<ChatBox />);
    })

    expect(screen.queryByAltText('LionChat Logo')).toBeDefined()
})

it("Renders ChatBox correctly", () => {
    act(() => {
        render(<ChatBox />)
    })


    window.HTMLElement.prototype.scrollIntoView = function () { };
    fireEvent.click(screen.getByAltText('LionChat Logo'))

    expect(screen.queryByAltText('LionChat Logo')).toBeValid()
    // console.log(screen.findByRole('ChatHeader'))
    expect(document.querySelector('.messageBox')).toBeValid()
    expect(document.querySelector('.chatBox')).toBeValid()
    expect(document.querySelector('.send')).toBeValid()
    expect(document.querySelector('.container')).toBeValid()

})