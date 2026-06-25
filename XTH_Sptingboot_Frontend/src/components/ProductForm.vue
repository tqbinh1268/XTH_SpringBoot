<script setup>
import { computed, defineProps, defineEmits } from 'vue'

const props = defineProps({
  form: {
    type: Object,
    required: true
  },
  saving: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['save-product', 'reset-form', 'show-message'])

const isEditing = computed(() => Boolean(props.form.id))

function handleSaveProduct() {
  emit('save-product')
}

function handleResetForm() {
  emit('reset-form')
}
</script>

<template>
  <form class="product-form" @submit.prevent="handleSaveProduct">
    <div class="form-head">
      <h2>{{ isEditing ? `Sua san pham #${form.id}` : 'Them san pham' }}</h2>
      <button v-if="isEditing" class="text-button" type="button" @click="handleResetForm">Huy</button>
    </div>

    <label>
      Ten san pham
      <input v-model.trim="form.name" type="text" placeholder="Vi du: Laptop Dell XPS 15" required />
    </label>

    <label>
      Gia ban
      <input v-model="form.price" type="number" min="0" step="1000" placeholder="25000000" required />
    </label>

    <label>
      Tong tien
      <input v-model="form.total" type="number" min="0" step="1000" placeholder="25000000" required />
    </label>

    <label>
      Ma danh muc
      <input v-model="form.categoryId" type="number" min="1" placeholder="Bo trong neu chua can gan" />
    </label>

    <label>
      So luong
      <input v-model="form.quantity" type="number" min="0" step="1" placeholder="100" required />
    </label>

    <button class="primary-button" type="submit" :disabled="saving">
      {{ saving ? 'Dang luu...' : isEditing ? 'Cap nhat' : 'Them moi' }}
    </button>
  </form>
</template>

<style scoped>
/* Add any specific styles for ProductForm here if needed */
</style>