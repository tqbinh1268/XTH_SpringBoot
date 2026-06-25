<script setup lang="ts">
import { ref, onMounted, watch, computed, defineProps } from 'vue'
import MessageDisplay from './MessageDisplay.vue' // Corrected path based on provided context

const topProducts = ref([])
const loading = ref(false)
const message = ref('')
const messageType = ref('info')

const props = defineProps({
  initialN: {
    type: Number,
    default: 5
  }
})
const selectedN = ref(props.initialN)
const API_URL_TOP_PRODUCTS = computed(() => `http://localhost:8081/db/products/top?n=${selectedN.value}`)

function formatCurrency(value) {
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND',
    maximumFractionDigits: 0,
  }).format(Number(value || 0))
}

function showMessage(text, type = 'info') {
  message.value = text
  messageType.value = type
}

function getCategoryName(product) {
  return product.category?.categoryName || product.category?.name || 'Chua gan'
}

async function requestJson(url, options = {}) {
  const response = await fetch(url, options)

  if (!response.ok) {
    const detail = await response.text().catch(() => '')
    throw new Error(detail || `HTTP ${response.status}`)
  }

  if (response.status === 204) {
    return null
  }

  const text = await response.text()
  return text ? JSON.parse(text) : null
}

async function loadTopProducts() {
  loading.value = true
  try {
    topProducts.value = await requestJson(API_URL_TOP_PRODUCTS.value)
    showMessage(`Da tai danh sach ${selectedN.value} san pham ban chay.`, 'success')
  } catch (error) {
    topProducts.value = []
    showMessage(`Khong the tai du lieu san pham ban chay. Kiem tra API ${API_URL_TOP_PRODUCTS.value}. ${error.message}`, 'error')
  } finally {
    loading.value = false
  }
}

onMounted(loadTopProducts)
watch(selectedN, loadTopProducts) // Watch for changes in selectedN and reload products
</script>

<template>
  <section class="top-products-panel">
    <div class="table-head">
      <h2>Top {{ selectedN }} San pham ban chay</h2>
      <div class="controls">
        <label for="top-n-select">Hiển thị top:</label>
        <select id="top-n-select" v-model="selectedN" :disabled="loading">
          <option value="3">3</option>
          <option value="5">5</option>
          <option value="10">10</option>
        </select>
      </div>
      <span>{{ loading ? 'Dang dong bo' : `${topProducts.length} dong` }}</span>
    </div>

    <MessageDisplay :message="message" :message-type="messageType" />

    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Ten san pham</th>
            <th>Danh muc</th>
            <th>Gia</th>
            <th>So luong ban</th>
            <th>So luong ton kho</th>
            <th>Tong tien</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td colspan="5" class="empty-cell">Dang tai du lieu...</td>
          </tr>
          <tr v-else-if="topProducts.length === 0">
            <td colspan="5" class="empty-cell">Chua co san pham ban chay nao.</td>
          </tr>
          <tr v-for="product in topProducts" :key="product.id">
            <td class="id-cell">#{{ product.id }}</td>
            <td>{{ product.name }}</td>
            <td>{{ getCategoryName(product) }}</td>
            <td class="price-cell">{{ formatCurrency(product.price) }}</td>
            <td>{{ product.soldQuantity }}</td>
            <td>{{ product.quantity }}</td>
            <td class="price-cell">{{ formatCurrency(product.total) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<style scoped>
/* Add any specific styles for TopProducts here if needed */
.top-products-panel {
  grid-column: 1 / -1; /* Span full width if placed in a grid layout */
}

.table-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.controls {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
</style>