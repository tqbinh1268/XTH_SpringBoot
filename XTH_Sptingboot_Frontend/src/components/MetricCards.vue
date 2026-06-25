<script setup>
import { defineProps } from 'vue'

defineProps({
  productCount: {
    type: Number,
    required: true
  },
  totalValue: {
    type: Number,
    required: true
  },
  formatCurrency: {
    type: Function,
    required: true
  }
})
</script>

<template>
  <section class="metrics">
    <div class="metric-card">
      <span>So san pham</span>
      <strong>{{ productCount }}</strong>
    </div>
    <div class="metric-card">
      <span>Tong gia tri</span>
      <strong>{{ formatCurrency(totalValue) }}</strong>
    </div>
  </section>
</template>

<style scoped>
/* Add any specific styles for MetricCards here if needed */
</style>