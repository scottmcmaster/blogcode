package com.smcmaster.functionaljava;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Map wrapper that helps with leveraging functional programming features of Java 8+.
 * 
 * @author smcmaster
 */
public class FuncMap<K, V> implements Map<K, V> {

  /**
   * Wrapped map.
   */
  private final Map<K, V> inner;

  public FuncMap(Map<K, V> inner) {
    this.inner = inner;
  }

  /**
   * Delegated methods.
   */
  public int size() {
    return inner.size();
  }

  public boolean isEmpty() {
    return inner.isEmpty();
  }

  public boolean containsKey(Object key) {
    return inner.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return inner.containsValue(value);
  }

  public V get(Object key) {
    return inner.get(key);
  }

  public V put(K key, V value) {
    return inner.put(key, value);
  }

  public V remove(Object key) {
    return inner.remove(key);
  }

  public void putAll(Map<? extends K, ? extends V> m) {
    inner.putAll(m);
  }

  public void clear() {
    inner.clear();
  }

  public Set<K> keySet() {
    return inner.keySet();
  }

  public Collection<V> values() {
    return inner.values();
  }

  public Set<Entry<K, V>> entrySet() {
    return inner.entrySet();
  }

  public V getOrDefault(Object key, V defaultValue) {
    return inner.getOrDefault(key, defaultValue);
  }

  public void forEach(BiConsumer<? super K, ? super V> action) {
    inner.forEach(action);
  }

  public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
    inner.replaceAll(function);
  }

  public V putIfAbsent(K key, V value) {
    return inner.putIfAbsent(key, value);
  }

  public boolean remove(Object key, Object value) {
    return inner.remove(key, value);
  }

  public boolean replace(K key, V oldValue, V newValue) {
    return inner.replace(key, oldValue, newValue);
  }

  public V replace(K key, V value) {
    return inner.replace(key, value);
  }

  public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
    return inner.computeIfAbsent(key, mappingFunction);
  }

  public V computeIfPresent(K key,
      BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    return inner.computeIfPresent(key, remappingFunction);
  }

  public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    return inner.compute(key, remappingFunction);
  }

  public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
    return inner.merge(key, value, remappingFunction);
  }

  /**
   * New methods.
   */
  public Optional<V> getOption(K key) {
    return Optional.ofNullable(get(key));
  }
}
