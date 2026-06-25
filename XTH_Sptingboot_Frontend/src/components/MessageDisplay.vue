<script setup>
import { defineProps } from 'vue'

defineProps({
  message: {
    type: String,
    default: ''
  },
  messageType: {
    type: String,
    default: 'info' // 'info', 'success', 'error'
  }
})
</script>

<template>
  <p v-if="message" class="notice" :class="messageType">{{ message }}</p>
</template>

<style scoped>
/* Add any specific styles for MessageDisplay here if needed */
</style>