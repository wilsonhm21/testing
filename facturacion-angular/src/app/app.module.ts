import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClienteComponent } from './page/cliente/cliente.component';
import { ProductoComponent } from './page/producto/producto.component';
import { FacturaComponent } from './page/factura/factura.component';
import { MenuComponent } from './components/menu/menu.component';

import { Cod404Component } from './components/cod404/cod404.component';
import { NuevoComponent } from './components/clientes/nuevo/nuevo.component';
import { HomeClienteComponent } from './components/clientes/home-cliente/home-cliente.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HomeProductoComponent } from './components/productos/home-producto/home-producto.component';
import { NuevoProductoComponent } from './components/productos/nuevo-producto/nuevo-producto.component';
import { EditarClienteComponent } from './components/clientes/editar-cliente/editar-cliente.component';
import { EditarProductoComponent } from './components/productos/editar-producto/editar-producto.component';
import { LoginComponent } from './page/login/login.component';
import { PageModule } from './page/page.module';
import { CabfacturaComponent } from './components/facturas/cabfactura/cabfactura.component';
import { ListaFacturasComponent } from './components/facturas/lista-facturas/lista-facturas.component';
import { ProveedorComponent } from './page/proveedor/proveedor.component';
import { HomeProveedorComponent } from './components/proveedor/home-proveedor/home-proveedor.component';
import { EditarProveedorComponent } from './components/proveedor/editar-proveedor/editar-proveedor.component';

@NgModule({
  declarations: [
    AppComponent,
    ProveedorComponent,
    ClienteComponent,
    ProductoComponent,
    FacturaComponent,
    MenuComponent,
    HomeClienteComponent,
    HomeProveedorComponent,
    Cod404Component,
    NuevoComponent,
    HomeProductoComponent,
    NuevoProductoComponent,
    EditarClienteComponent,
    EditarProveedorComponent,
    EditarProductoComponent,
    LoginComponent,
    CabfacturaComponent,
    ListaFacturasComponent,
   

    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    // PageModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
