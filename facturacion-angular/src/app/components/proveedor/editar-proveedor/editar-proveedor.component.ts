import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProveedorService } from 'src/app/service/proveedor.service';
import { Router } from '@angular/router';
import { soloTexto, validarCorreo, validarDecimalConDosDecimales } from '../../../validators/validatorFn';


@Component({
  selector: 'app-editar-proveedor',
  templateUrl: './editar-proveedor.component.html',
  styleUrls: ['./editar-proveedor.component.css']
})
export class EditarProveedorComponent {

  
  @Input() proveedorEditar: any = {};
  @Output() modoOcultopro = new EventEmitter();
  personaForm: FormGroup;


  constructor(private fb: FormBuilder, private proveedorService: ProveedorService) {
    this.personaForm = this.fb.group({
      idProveedor: '',
      ruc: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9]*'), Validators.maxLength(15)]],
      nombre: ['', [Validators.required, soloTexto()]],
      direccion: ['', [Validators.required,]],
      correo: ['', [Validators.required, validarCorreo()]],
      fechaCreacion: ['', [Validators.required]],
  
    });

    console.log("constructor");
    
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['personaEditar'] && this.proveedorEditar) {
      this.personaForm.patchValue(this.proveedorEditar);
    }
    console.log("onchange");
  }
  

  guardar(): void {

    const valoresFormulario = this.personaForm.value;
    console.log("Persona ", this.proveedorEditar?.nombre);
    console.log("Persona editada", valoresFormulario);
    
    if (this.personaForm.valid) {
      
      console.log('El formulario es válido. Enviar solicitud...');
    } else {
      
      Object.values(this.personaForm.controls).forEach(control => {
        control.markAsTouched();
      });
      return;
    }
    
    this.proveedorService.actualizar(valoresFormulario).subscribe(
      response => {
        console.log('proveedor editada correctamente:', response);
        alert('proveedor editado correctamente');
        // window.location.reload();
        this.modoOcultopro.emit();
      },
      error => {
        console.error('Error al editar persona:', error);
        alert('Error al editar proveedor: los campos no cumplen con los formatos requeridos');	
      }
    )
  }

}
