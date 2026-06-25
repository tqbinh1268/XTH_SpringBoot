<script setup>
import { defineProps, defineEmits } from 'vue'

defineProps({
  products: {
    type: Array,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  },
  deletingId: {
    type: [String, Number],
    default: null
  },
  productCount: {
    type: Number,
    required: true
  },
  formatCurrency: {
    type: Function,
    required: true
  },
  getCategoryName: {
    type: Function,
    required: true
  }
})

const emit = defineEmits(['edit-product', 'delete-product'])

function handleEditProduct(product) {
  emit('edit-product', product)
}

function handleDeleteProduct(product) {
  emit('delete-product', product)
}
</script>

<template>
  <section class="table-panel">
    <div class="table-head">
      <h2>Danh sach san pham</h2>
      <span>{{ loading ? 'Dang dong bo' : `${productCount} dong` }}</span>
    </div>

    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Ten san pham</th>
            <th>Danh muc</th>
            <th>Gia</th>
            <th>So luong</th>
            <th>Tong tien</th>
            <th>Thao tac</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="loading">
            <td colspan="5" class="empty-cell">Dang tai du lieu...</td>
          </tr>
          <tr v-else-if="products.length === 0">
            <td colspan="5" class="empty-cell">Chua co san pham.</td>
          </tr>
          <tr v-for="product in products" v-else :key="product.id">
            <td class="id-cell">#{{ product.id }}</td>
            <td>{{ product.name }}</td>
            <td>{{ getCategoryName(product) }}</td>
            <td class="price-cell">{{ formatCurrency(product.price) }}</td>
            <td>{{ product.quantity }}</td>
            <td class="price-cell">{{ formatCurrency(product.total) }}</td>
            <td>
              <div class="actions">
                <button type="button" class="icon-button edit" title="Sua" @click="handleEditProduct(product)">
                  Sua
                </button>
                <button
                  type="button"
                  class="icon-button delete"
                  title="Xoa"
                  :disabled="deletingId === product.id"
                  @click="handleDeleteProduct(product)"
                >
                  {{ deletingId === product.id ? '...' : 'Xoa' }}
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>

<style scoped>
/* Add any specific styles for ProductTable here if needed */
</style>