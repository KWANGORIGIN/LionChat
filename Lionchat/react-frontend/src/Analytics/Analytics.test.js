import React from "react";

import Analytics from './Analytics';
import { render, screen } from '@testing-library/react'
import { act } from 'react-dom/test-utils'

it("Renders Analytics correctly", () => {
    act(() => {
        render(<Analytics />)
    })

    screen.getByText('Total Questions Asked:', {exact: false})
    screen.getByText('Questions Asked:')
    screen.getByText('Number Questions Per Topic:', {exact: false})
    screen.getByText('Average Ratings:', {exact: false})
    screen.getByText('Number Misclassifications Per Topic:', {exact: false})
    screen.getByText('Number Inappropriate Queries:', {exact: false})
    screen.getByText('Inappropriate Queries:')
})