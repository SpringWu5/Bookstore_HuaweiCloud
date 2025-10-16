import { describe, it, expect, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import AiAssistant from '../src/components/AiAssistant.vue'

describe('AiAssistant', () => {
  let wrapper

  beforeEach(() => {
    // Create a clean wrapper for each test
    wrapper = mount(AiAssistant, {
      attachTo: document.body
    })
  })

  it('renders properly', () => {
    expect(wrapper.exists()).toBe(true)
    expect(wrapper.find('.fab').exists()).toBe(true)
  })

  it('has initial state correctly set', () => {
    // Check if the FAB is visible initially
    expect(wrapper.find('.fab').exists()).toBe(true)
    
    // Check if dialog is hidden initially (should not exist in DOM until opened)
    expect(wrapper.find('.chat-dialog').exists()).toBe(false)
  })

  it('toggles dialog visibility when FAB is clicked', async () => {
    // Initially dialog should not be visible
    expect(wrapper.find('.chat-dialog').exists()).toBe(false)
    
    // Click the FAB to show dialog
    const fab = wrapper.find('.fab')
    await fab.trigger('click')
    await wrapper.vm.$nextTick() // Wait for re-render
    
    // Dialog should now be visible
    expect(wrapper.find('.chat-dialog').exists()).toBe(true)
    
    // Click again to hide
    await fab.trigger('click')
    await wrapper.vm.$nextTick() // Wait for re-render
    
    // Dialog should be hidden again
    expect(wrapper.find('.chat-dialog').exists()).toBe(false)
  })

  it('displays initial AI message after opening dialog', async () => {
    // Open the dialog
    await wrapper.find('.fab').trigger('click')
    await wrapper.vm.$nextTick() // Wait for re-render
    
    // Check if initial AI message is displayed
    const aiMessages = wrapper.findAll('.chat-message.assistant')
    expect(aiMessages.length).toBeGreaterThan(0)
    
    // The first AI message should be present
    const firstMessage = aiMessages[0]
    expect(firstMessage.text()).toContain('你好')
  })
})