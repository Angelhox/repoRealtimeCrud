package com.example.realtimework.Modelo;

public class Productos {
   private String codigoProducto;
   private String nombreProducto;
   private String stockProducto;
   private String precioCosto;
   private String precioVenta;

   public String getCodigoProducto() {
      return codigoProducto;
   }

   public void setCodigoProducto(String codigoProducto) {
      this.codigoProducto = codigoProducto;
   }

   public String getNombreProducto() {
      return nombreProducto;
   }

   public void setNombreProducto(String nombreProducto) {
      this.nombreProducto = nombreProducto;
   }

   public String getStockProducto() {
      return stockProducto;
   }

   public void setStockProducto(String stockProducto) {
      this.stockProducto = stockProducto;
   }

   public String getPrecioCosto() {
      return precioCosto;
   }

   public void setPrecioCosto(String precioCosto) {
      this.precioCosto = precioCosto;
   }

   public String getPrecioVenta() {
      return precioVenta;
   }

   public void setPrecioVenta(String precioVenta) {
      this.precioVenta = precioVenta;
   }

   public Productos(String codigoProducto, String nombreProducto, String stockProducto, String precioCosto, String precioVenta) {
      this.codigoProducto = codigoProducto;
      this.nombreProducto = nombreProducto;
      this.stockProducto = stockProducto;
      this.precioCosto = precioCosto;
      this.precioVenta = precioVenta;
   }

   public Productos() {
   }
}

