<template>
  <div class="ai-assistant">
    <!-- Chat Dialog -->
    <div v-if="isDialogVisible" class="chat-dialog">
      <div class="dialog-header" @mousedown="startDragDialog">
        <h3 class="title">智淘助手</h3>
        <span class="beijing-time">北京时间 <span class="beijing-time-num">{{ beijingTime }}</span></span>
        <button class="close-btn" @click="closeDialog">&times;</button>
      </div>
      <div class="dialog-body" ref="chatBody">
        <div v-for="(message, index) in messages" :key="index" :class="['chat-message', message.role]">
          <template v-if="message.role === 'assistant'">
            <span class="avatar avatar-ai">
              <svg width="38" height="38" viewBox="0 0 38 38" xmlns="http://www.w3.org/2000/svg">
                <circle cx="19" cy="19" r="19" fill="#ff6700"/>
                <text x="50%" y="56%" text-anchor="middle" fill="#fff" font-size="18" font-family="Arial, sans-serif" font-weight="bold" dominant-baseline="middle">AI</text>
              </svg>
            </span>
            <div class="ai-text-block">
              <template v-for="(para, pIdx) in parseParagraphs(message.content)" :key="pIdx">
                <div class="ai-text">
                  {{ para }}
                  <span v-if="isLoading && index === messages.length - 1 && pIdx === parseParagraphs(message.content).length - 1" class="blinking-cursor">|</span>
                </div>
              </template>
            </div>
          </template>
          <template v-else>
            <div class="bubble-container user">
              <template v-for="(para, pIdx) in parseParagraphs(message.content)" :key="pIdx">
                <div class="ds-bubble user">
                  {{ para }}
                </div>
              </template>
            </div>
            <!-- 用户消息不显示头像 -->
          </template>
        </div>
        <!-- AI思考动画 -->
        <div v-if="isThinking" class="chat-message assistant">
          <span class="avatar avatar-ai">
            <svg width="38" height="38" viewBox="0 0 38 38" xmlns="http://www.w3.org/2000/svg">
              <circle cx="19" cy="19" r="19" fill="#ff6700"/>
              <text x="50%" y="56%" text-anchor="middle" fill="#fff" font-size="18" font-family="Arial, sans-serif" font-weight="bold" dominant-baseline="middle">AI</text>
            </svg>
          </span>
          <div class="ai-text-block">
            <div class="ai-text">正在思考中{{ thinkingDots }}</div>
          </div>
        </div>
      </div>
      <!-- Suggestion Bar: 移到输入框上方 -->
      <div class="suggestion-bar">
        <span v-for="(tip, idx) in suggestions" :key="idx" class="suggestion-chip" @click="handleSuggestionClick(tip)">{{ tip }}</span>
      </div>
      <div class="dialog-footer">
        <input v-model="userInput" @keyup.enter="sendMessage" placeholder="你好，有什么可以帮您？" />
        <button @click="sendMessage">发送</button>
      </div>
    </div>

    <!-- Floating Action Button (FAB) -->
    <div
      class="fab"
      @click="toggleDialog"
      @mousedown="startDragFab"
      :style="{ top: fabPosition.y + 'px', left: fabPosition.x + 'px' }"
    >
      <span class="fab-icon">AI</span>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, onUnmounted } from 'vue';
import axios from 'axios';

const suggestions = [
  '如何发布二手商品？',
  '如何快速卖出我的物品？',
  '如何联系买家或卖家？',
  '如何在校园内安全交易？',
  '如何查看和管理我的订单？',
  '如何申请售后或退款？',
  '如何举报违规或虚假信息？',
  '如何查看校园公告和活动？',
  '如何评价和收藏商品？',
  '如何参加校园二手集市活动？',
  '如何查看平台交易规则？',
  '如何找回账号密码？',
];

// --- State Management ---
const isDialogVisible = ref(false);
const messages = ref([
  { role: 'assistant', content: '你好！我是您的AI智能助手，有什么可以帮到您吗？' }
]);
const userInput = ref('');
const isLoading = ref(false);
const chatBody = ref(null);
const isThinking = ref(false);
const thinkingDots = ref('');
let thinkingInterval = null;

// --- Positions ---
const fabPosition = ref({ x: window.innerWidth - 80, y: window.innerHeight - 80 });

// --- Draggability ---
let dragState = {
  isDragging: false,
  target: null, // 'fab' or 'dialog'
  initialMouseX: 0,
  initialMouseY: 0,
  initialElX: 0,
  initialElY: 0
};

const startDragFab = (event) => {
  startDrag(event, 'fab');
};

const startDragDialog = (event) => {
  startDrag(event, 'dialog');
};

const startDrag = (event, target) => {
  dragState.isDragging = true;
  dragState.target = target;
  dragState.initialMouseX = event.clientX;
  dragState.initialMouseY = event.clientY;

  const position = target === 'fab' ? fabPosition.value : { x: 0, y: 0 };
  dragState.initialElX = position.x;
  dragState.initialElY = position.y;

  document.addEventListener('mousemove', onDrag);
  document.addEventListener('mouseup', stopDrag);
};

const onDrag = (event) => {
  if (!dragState.isDragging) return;
  
  const dx = event.clientX - dragState.initialMouseX;
  const dy = event.clientY - dragState.initialMouseY;
  
  const newX = dragState.initialElX + dx;
  const newY = dragState.initialElY + dy;

  const position = dragState.target === 'fab' ? fabPosition.value : { x: newX, y: newY };
  position.x = newX;
  position.y = newY;
};

const stopDrag = () => {
  dragState.isDragging = false;
  document.removeEventListener('mousemove', onDrag);
  document.removeEventListener('mouseup', stopDrag);
};


// --- Dialog and Chat Logic ---
const toggleDialog = () => {
  if (dragState.isDragging) return; // Prevent toggling when finishing a drag
  isDialogVisible.value = !isDialogVisible.value;
};

const closeDialog = () => {
  isDialogVisible.value = false;
};

const scrollToBottom = () => {
  nextTick(() => {
    if (chatBody.value) {
      chatBody.value.scrollTop = chatBody.value.scrollHeight;
    }
  });
};

const dialogWidth = `${Math.round(window.innerWidth * 0.6)}px`;

function startThinkingAnimation() {
  isThinking.value = true;
  thinkingDots.value = '';
  let dotCount = 0;
  thinkingInterval = setInterval(() => {
    dotCount = (dotCount + 1) % 4;
    thinkingDots.value = '.'.repeat(dotCount);
  }, 400);
}
function stopThinkingAnimation() {
  isThinking.value = false;
  thinkingDots.value = '';
  if (thinkingInterval) clearInterval(thinkingInterval);
}
onUnmounted(() => {
  stopThinkingAnimation();
});

const sendMessage = async (customInput) => {
  const input = typeof customInput === 'string' ? customInput : userInput.value;
  if (!input.trim() || isLoading.value) return;

  const userMessage = { role: 'user', content: input };
  messages.value.push(userMessage);
  isLoading.value = true;
  userInput.value = '';
  scrollToBottom();

  // 先插入一个空的AI消息
  const aiMessage = { role: 'assistant', content: '' };
  messages.value.push(aiMessage);
  scrollToBottom();

  startThinkingAnimation();

  try {
    const apiKey = 'sk-cafc9a423f834e1d8161179602918f88';
    const response = await fetch('https://api.deepseek.com/chat/completions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${apiKey}`
      },
      body: JSON.stringify({
        model: 'deepseek-chat',
        messages: messages.value.slice(0, -1), // 不带空AI消息
        stream: true,
        max_tokens: 1200
      })
    });

    if (!response.body) throw new Error('No stream body');
    const reader = response.body.getReader();
    let buffer = '';
    const decoder = new TextDecoder();
    let pendingContent = '';
    let animationFrameId = null;
    function appendContent(newText) {
      pendingContent += newText;
      if (!animationFrameId) {
        animationFrameId = requestAnimationFrame(() => {
          aiMessage.content += pendingContent;
          pendingContent = '';
          scrollToBottom();
          animationFrameId = null;
        });
      }
    }
    while (true) {
      const { done, value } = await reader.read();
      if (done) break;
      buffer += decoder.decode(value, { stream: true });
      // 解析每一行的data
      const lines = buffer.split('\n');
      buffer = lines.pop(); // 最后一行可能不完整，留到下次
      for (const line of lines) {
        if (line.startsWith('data: ')) {
          const data = line.replace('data: ', '').trim();
          if (data === '[DONE]') break;
          try {
            const delta = JSON.parse(data);
            if (delta.choices && delta.choices[0].delta && delta.choices[0].delta.content) {
              appendContent(delta.choices[0].delta.content);
            }
          } catch (e) {}
        }
      }
    }
    // 最后一批内容
    if (pendingContent) {
      aiMessage.content += pendingContent;
      scrollToBottom();
    }
  } catch (error) {
    aiMessage.content = '抱歉，我暂时无法回答您的问题。';
  } finally {
    isLoading.value = false;
    stopThinkingAnimation();
    scrollToBottom();
  }
};

const handleSuggestionClick = (tip) => {
  sendMessage(tip);
};

function parseParagraphs(text) {
  // 按换行分段，保留"1、"等编号
  return text ? text.split(/\n+/).map(p => p.trim()).filter(Boolean) : [];
}

// 北京时间显示
const beijingTime = ref('');
let timeInterval = null;
function updateBeijingTime() {
  const now = new Date();
  // 转为北京时间
  const utc = now.getTime() + now.getTimezoneOffset() * 60000;
  const beijing = new Date(utc + 8 * 3600000);
  const y = beijing.getFullYear();
  const m = String(beijing.getMonth() + 1).padStart(2, '0');
  const d = String(beijing.getDate()).padStart(2, '0');
  const h = String(beijing.getHours()).padStart(2, '0');
  const min = String(beijing.getMinutes()).padStart(2, '0');
  const s = String(beijing.getSeconds()).padStart(2, '0');
  beijingTime.value = `${y}-${m}-${d} ${h}:${min}:${s}`;
}
timeInterval = setInterval(updateBeijingTime, 1000);
updateBeijingTime();
onUnmounted(() => { clearInterval(timeInterval); });
</script>

<style scoped>
.ai-assistant {
  position: fixed;
  z-index: 1000;
}

/* Floating Action Button (FAB) */
.fab {
  position: fixed;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: linear-gradient(145deg, #ff6700, #e55300);
  box-shadow: 0 4px 12px rgba(255,103,0,0.18);
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: grab;
  user-select: none;
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease;
}
.fab:active {
  cursor: grabbing;
  transform: scale(0.95);
  box-shadow: 0 2px 6px rgba(255,103,0,0.18);
}
.fab-icon {
  color: #fff;
  font-size: 28px;
  font-weight: 900;
  font-family: 'PingFang SC', 'Microsoft YaHei', 'Arial Rounded MT Bold', 'Arial', sans-serif;
  letter-spacing: 1.5px;
  padding-bottom: 1px;
  border-radius: 12px;
}

/* Chat Dialog */
.chat-dialog {
  position: fixed;
  left: 50%;
  top: 15%;
  transform: translate(-50%, 0);
  width: 60vw;
  height: 600px;
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(255,103,0,0.13);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  font-family: 'Microsoft YaHei', 'Segoe UI', sans-serif;
  z-index: 1001;
}
.dialog-header {
  background: linear-gradient(to right, #ff6700, #e55300);
  color: white;
  padding: 15px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: move;
  position: relative;
}
.dialog-header .title {
  font-size: 18px;
  font-weight: 600;
  margin: 0;
  flex: 1 1 auto;
}
.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 28px;
  cursor: pointer;
  line-height: 1;
  padding: 0;
  opacity: 0.8;
  transition: opacity 0.2s;
}
.close-btn:hover {
  opacity: 1;
}
.dialog-body {
  flex-grow: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f6f7f9;
}
.chat-message {
  display: flex;
  align-items: flex-end;
  margin-bottom: 18px;
}
.chat-message.assistant {
  flex-direction: row;
}
.chat-message.user {
  flex-direction: row-reverse;
}
.avatar {
  width: 38px;
  height: 38px;
  border-radius: 50%;
  object-fit: cover;
  margin: 0 10px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}
.avatar-ai svg {
  display: block;
}
.ai-text-block {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-left: 0;
}
.ai-text {
  font-size: 15px;
  color: #222;
  background: none;
  padding: 0;
  margin: 0 0 10px 0;
  line-height: 1;
  word-break: break-all;
  white-space: pre-line;
}
.bubble-container {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.ds-bubble {
  max-width: 520px;
  min-width: 40px;
  padding: 14px 20px;
  border-radius: 18px;
  font-size: 15px;
  line-height: 1.7;
  word-break: break-all;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
  margin-bottom: 0;
  white-space: pre-line;
}
.ds-bubble.ai {
  background: #fff;
  color: #222;
  border-bottom-left-radius: 6px;
}
.ds-bubble.user {
  background: #ff6700;
  color: #fff;
  border-bottom-right-radius: 6px;
  margin-left: auto;
}
.dialog-footer {
  padding: 15px 20px;
  display: flex;
  border-top: 1px solid #ffd6b3;
  background-color: #ffffff;
}
.dialog-footer input {
  flex-grow: 1;
  border: 1px solid #ff6700;
  border-radius: 20px;
  padding: 10px 18px;
  font-size: 14px;
  outline: none;
  transition: border-color 0.2s;
}
.dialog-footer input:focus {
  border-color: #e55300;
}
.dialog-footer button {
  margin-left: 10px;
  padding: 10px 20px;
  border: none;
  background-color: #ff6700;
  color: white;
  border-radius: 20px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s;
}
.dialog-footer button:hover {
  background-color: #e55300;
}
.fab:hover {
  background-color: #e55300;
}
.suggestion-bar {
  position: static;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  gap: 8px;
  padding: 8px 20px 0 20px;
}
.suggestion-chip {
  background-color: #fcefe6;
  color: #ff6700;
  padding: 6px 12px;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 13px;
  border: 1px solid #ffddc9;
}
.suggestion-chip:hover {
  background-color: #ff6700;
  color: white;
  border-color: #ff6700;
}
.blinking-cursor {
  display: inline-block;
  width: 1ch;
  color: #ff6700;
  animation: blink 1s steps(1) infinite;
}
@keyframes blink {
  0%, 50% { opacity: 1; }
  51%, 100% { opacity: 0; }
}
.beijing-time {
  display: inline-block;
  background: rgba(255,255,255,0.85);
  color: #ff6700;
  font-size: 16px;
  font-family: 'JetBrains Mono', 'Fira Mono', 'Consolas', 'Arial Rounded MT Bold', 'Arial', sans-serif;
  font-weight: 600;
  border-radius: 16px;
  padding: 2px 16px 2px 10px;
  margin-left: 12px;
  margin-right: 24px;
  box-shadow: 0 2px 8px rgba(255,103,0,0.08);
  vertical-align: middle;
  user-select: none;
  letter-spacing: 1px;
  position: relative;
}
.beijing-time-num {
  color: #ff6700;
  font-weight: bold;
  font-family: 'JetBrains Mono', 'Fira Mono', 'Consolas', 'Arial Rounded MT Bold', 'Arial', sans-serif;
  font-size: 16px;
  letter-spacing: 1px;
}
</style> 