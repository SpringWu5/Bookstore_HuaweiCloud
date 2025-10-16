import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import AiAssistant from '../src/components/AiAssistant.vue'

describe('AiAssistant', () => {
  it('renders properly', () => {
    const wrapper = mount(AiAssistant)
    expect(wrapper.exists()).toBe(true)
  })

  it('has initial state correctly set', () => {
    const wrapper = mount(AiAssistant)
    
    // Check if the FAB is visible initially
    expect(wrapper.find('.fab').exists()).toBe(true)
    
    // Check if dialog is hidden initially
    expect(wrapper.find('.chat-dialog').exists()).toBe(false)
  })

  it('toggles dialog visibility when FAB is clicked', async () => {
    const wrapper = mount(AiAssistant)
    
    // Initially dialog should not be visible
    expect(wrapper.find('.chat-dialog').exists()).toBe(false)
    
    // Click the FAB to show dialog
    await wrapper.find('.fab').trigger('click')
    
    // Dialog should now be visible
    expect(wrapper.find('.chat-dialog').exists()).toBe(true)
    
    // Click again to hide
    await wrapper.find('.fab').trigger('click')
    
    // Dialog should be hidden again
    expect(wrapper.find('.chat-dialog').exists()).toBe(false)
  })

  it('displays initial AI message', async () => {
    const wrapper = mount(AiAssistant)
    
    // Open the dialog
    await wrapper.find('.fab').trigger('click')
    
    // Check if initial AI message is displayed
    const messages = wrapper.findAll('.chat-message')
    expect(messages.length).toBeGreaterThan(0)
    
    // The first message should be from the assistant
    const firstMessage = messages[0]
    expect(firstMessage.classes()).toContain('assistant')
  })
})