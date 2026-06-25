<script setup>
import { computed, onMounted, reactive, ref } from 'vue'

import MessageDisplay from './components/MessageDisplay.vue'
import MetricCards from './components/MetricCards.vue'
import ProductForm from './components/ProductForm.vue'
import TopProducts from './components/TopProducts.vue'
import ProductTable from './components/ProductTable.vue'
const API_URL = 'http://localhost:8081/db/products'

const products = ref([])
const loading = ref(false)
const saving = ref(false)
const deletingId = ref(null)
const message = ref('')
const messageType = ref('info')

const form = reactive({
  id: '',
  name: '',
  price: '',
  categoryId: '',
  total: '',
})

const isEditing = computed(() => Boolean(form.id))
const productCount = computed(() => products.value.length)
const totalValue = computed(() =>
  products.value.reduce((sum, product) => sum + Number(product.price || 0), 0),
)

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

function resetForm() {
  form.id = ''
  form.name = ''
  form.price = ''
  form.categoryId = ''
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

async function loadProducts() {
  loading.value = true

  try {
    products.value = await requestJson(API_URL)
    showMessage('Da tai danh sach san pham.', 'success')
  } catch (error) {
    products.value = []
    showMessage(`Khong the tai du lieu. Kiem tra API ${API_URL}. ${error.message}`, 'error')
  } finally {
    loading.value = false
  }
}

function handleEditProduct(product) {
  form.id = product.id
  form.name = product.name || ''
  form.price = product.price ?? ''
  form.categoryId = product.category?.id || ''
  form.total = product.total ?? ''
  form.quantity = product.quantity ?? ''
  showMessage(`Dang sua san pham #${product.id}.`, 'info')
}

async function handleSaveProduct() {
  const name = form.name.trim()
  const price = Number(form.price)

  if (!name) {
    showMessage('Vui long nhap ten san pham.', 'error')
    return
  }

  if (!Number.isFinite(price) || price < 0) {
    showMessage('Gia san pham phai la so khong am.', 'error')
    return
  }

  const payload = {
    name,
    price,
    total: Number(form.total),
    quantity: Number(form.quantity),
  }

  if (form.categoryId) {
    payload.category = { id: Number(form.categoryId) }
  }

  const url = isEditing.value ? `${API_URL}/${form.id}` : API_URL
  const method = isEditing.value ? 'PUT' : 'POST'
  saving.value = true

  try {
    await requestJson(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
    showMessage(isEditing.value ? 'Cap nhat san pham thanh cong.' : 'Them san pham thanh cong.', 'success')
    resetForm()
    await loadProducts()
  } catch (error) {
    showMessage(`Luu san pham that bai. ${error.message}`, 'error')
  } finally {
    saving.value = false
  }
}

async function handleDeleteProduct(product) {
  const confirmed = window.confirm(`Xoa san pham "${product.name}"?`)

  if (!confirmed) {
    return
  }

  deletingId.value = product.id

  try {
    await requestJson(`${API_URL}/${product.id}`, { method: 'DELETE' })
    showMessage(`Da xoa san pham #${product.id}.`, 'success')
    if (form.id === product.id) {
      resetForm()
    }
    await loadProducts()
  } catch (error) {
    showMessage(`Xoa san pham that bai. ${error.message}`, 'error')
  } finally {
    deletingId.value = null
  }
}

onMounted(loadProducts)
</script>

<template>
  <main class="app-shell">
    <header class="page-head">
      <div>
        <p class="eyebrow">API: {{ API_URL }}</p>
        <h1>Quan ly san pham</h1>
      </div>
      <button class="secondary-button" type="button" :disabled="loading" @click="loadProducts">
        {{ loading ? 'Dang tai...' : 'Tai lai' }}
      </button>
    </header>

    <MetricCards :product-count="productCount" :total-value="totalValue" :format-currency="formatCurrency" />

    <MessageDisplay :message="message" :message-type="messageType" />

    <section class="content-grid">
      <ProductForm
        :form="form"
        :saving="saving"
        @save-product="handleSaveProduct"
        @reset-form="resetForm"
        @show-message="showMessage"
      />

      <ProductTable
        :products="products"
        :loading="loading"
        :deleting-id="deletingId"
        :product-count="productCount"
        :format-currency="formatCurrency"
        :get-category-name="getCategoryName"
        @edit-product="handleEditProduct"
        @delete-product="handleDeleteProduct"
      />

    </section>

    <!-- New section for TopProducts -->
    <TopProducts :initial-n="5" />
  </main>
</template>
