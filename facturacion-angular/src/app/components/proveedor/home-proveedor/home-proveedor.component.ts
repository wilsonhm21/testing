import { Component } from '@angular/core';
import { ProveedorService } from 'src/app/service/proveedor.service';
@Component({
  selector: 'app-home-proveedor',
  templateUrl: './home-proveedor.component.html',
  styleUrls: ['./home-proveedor.component.css']
})
export class HomeProveedorComponent {

  proveedor: any ; 
  proveedorEditar: any;
  filtroProveedor: any;
  modoOcultoPro: boolean = true;
  constructor(private proveedorService: ProveedorService) {
  }
  ngOnInit() {
   this.getData();
  }
  
  getData(){
    this.proveedorService.getData().subscribe(data => {
      this.proveedor = data;
      this.filtroProveedor = data;
      
    })
  }
  
  eliminarPorId(id: number) {
    console.log(id)
    this.proveedorService.eliminarPorId(id).subscribe(
      (response) => {
      console.log('Persona eliminada correctamente');
      this.getData();
    }, error => {
      console.error('Error al eliminar persona:', error);
    });
  }
  buscar(texto: Event) {
    const input = texto.target as HTMLInputElement;
    console.log(input.value);
    console.log(this.proveedor);
    this.filtroProveedor = this.proveedor.filter( (prove: any) =>
      prove.idProveedor.toString().includes(input.value.toLowerCase()) ||
      prove.ruc.toLowerCase().includes(input.value.toLowerCase()) ||
      prove.nombre.toLowerCase().includes(input.value.toLowerCase()) ||
      prove.direccion.toLowerCase().includes(input.value.toLowerCase()) ||
      prove.correo.toLowerCase().includes(input.value.toLowerCase())
    );
    console.log(this.filtroProveedor)
  }

  toggleModoEdicion(persona: any) {
    this.proveedorEditar = persona;
    this.editarModoOcutoho()
    console.log("algoooo*", this.proveedorEditar);
  }

  editarModoOcutoho(){
    this.modoOcultoPro = !this.modoOcultoPro;
    this.getData();
  }



}
