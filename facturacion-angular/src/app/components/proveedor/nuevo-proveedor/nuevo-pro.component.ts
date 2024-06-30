import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { soloTexto, validarCorreo, validarDecimalConDosDecimales } from 'src/app/validators/validatorFn';
import { ProveedorService } from 'src/app/service/proveedor.service';
@Component({
  selector: 'app-nuevo-proveedor',
  templateUrl: './nuevo-pro.component.html',
  styleUrls: ['./nuevo-pro.component.css']
})
export class NuevoComponent {

  formulariopro: FormGroup;
  existe: boolean = false;
  constructor(private formBuilder: FormBuilder, private proveedorService: ProveedorService) {
    this.formulariopro = this.formBuilder.group({
      ruc: ['', [Validators.required, Validators.pattern('[0-9]*'), Validators.maxLength(15)]],
      nombre: ['', [Validators.required, soloTexto()]],
      direccion: ['', [Validators.required]],
      correo: ['', [Validators.required, validarCorreo()]],
    });
  }

  onSubmitpro() {

    if (this.formulariopro.valid) {
      console.log('El formulario es válido. Enviar solicitud...');
    } else {
      Object.values(this.formulariopro.controls).forEach(control => {
        control.markAsTouched();
      });
      return;
    }

    this.proveedorService.enviarDatos(this.formulariopro.value).subscribe(response => {
      console.log('Datos enviados correctamente:', response);
      alert('Datos registrados correctamente');
      this.formulariopro.reset();
    }, error => {
      console.error('Error al enviar datos:', error);
      alert('Error al enviar datos: los campos no cumplen con los formatos requeridos');	
    });
  }

  validarCodigo(event: any) {
    const input = event.target as HTMLInputElement;
  
    // Eliminar cualquier validación anterior
    //this.formulario.get('codigo')!.setErrors(null);
    this.existe = false;
  
    const delay = 300;
  
    setTimeout(() => {
      this.proveedorService.verificarExistencia(input.value).subscribe(data => {
        if ( parseInt(data.data) > 0 ) {	
          this.existe = true;
          console.log('El código ya existe', data.data);

          this.formulariopro.get('rucDni')!.setErrors({ 'codigoExistente': true });
        } else {
          this.formulariopro.get('rucDni')!.setErrors(null);
          this.existe = false;
        }
      });
    }, delay);
  }


  
}
