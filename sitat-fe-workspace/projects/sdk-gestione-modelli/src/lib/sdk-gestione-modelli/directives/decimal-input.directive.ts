import { Directive, ElementRef, HostListener, Renderer2, Self } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[decimalInput]'
})
export class DecimalInputDirective {
  private readonly allowedKeys = [
    'Backspace', 'Delete', 'Tab', 'Escape', 'Enter',
    'ArrowLeft', 'ArrowRight', 'Home', 'End'
  ];
  private readonly allowedCtrlKeys = ['a', 'c', 'v', 'x'];

  constructor(
    private el: ElementRef, 
    private renderer: Renderer2,
    @Self() private control: NgControl
  ) {}

  @HostListener('keydown', ['$event'])
  onKeyDown(event: KeyboardEvent): boolean {
    if (this.allowedKeys.includes(event.key)) {
      return true;
    }

    if ((event.ctrlKey || event.metaKey) && this.allowedCtrlKeys.includes(event.key)) {
      return true;
    }

    const current = this.el.nativeElement.value;
    const position = this.el.nativeElement.selectionStart;
    
    if (event.key === ',' && (position === 0 || current.trim() === '')) {
      return false;
    }


    if (event.key === ',' && current.includes(',')) {
      return false;
    }

    const next = [current.slice(0, position), event.key, current.slice(position)].join('');
    return /^[0-9,]*$/.test(next);
  }

  @HostListener('input')
  onInput(): void {
    let value = this.el.nativeElement.value;
    
    if (!value) return;
    
    if (value.startsWith(',')) {
      this.updateValue('0' + value);
      return;
    }
    
    if (value === ',') {
      this.updateValue('');
      return;
    }
    
    
    value = this.removeLeadingZeros(value);
    
    this.validateDecimalFormat(value);
    
    this.updateValue(value);
  }

  @HostListener('blur')
  onBlur(): void {
    let value = this.el.nativeElement.value;
    
    if (!value) return;
    if (value === ',') {
      this.updateValue('');
      return;
    }
    
    if (value.endsWith(',')) {
      value = value.slice(0, -1);
    }
    
    if (value.includes(',')) {
      value = this.formatDecimal(value);
    } else if (value.length > 1 && value.startsWith('0')) {
      value = value.replace(/^0+/, '') || '0';
    }
    
    this.updateValue(value);
  }

  private updateValue(value: string): void {
    this.el.nativeElement.value = value;
    this.control.control.setValue(value, { emitEvent: false });
  }

  private removeLeadingZeros(value: string): string {
    if (!value) return value;
    
    if (value.includes(',')) {
      const parts = value.split(',');
      if (parts[0].length > 1 && parts[0].startsWith('0')) {
        parts[0] = parts[0].replace(/^0+/, '') || '0';
        return parts.join(',');
      }
    } else if (value.length > 1 && value.startsWith('0')) {
      return value.replace(/^0+/, '') || '0';
    }
    
    return value;
  }

  private validateDecimalFormat(value: string): void {
    if (!value || !value.includes(',')) return;
    
    const parts = value.split(',');
    const isInvalid = parts[0] === '' || parts[1] === '';
    
    if (isInvalid) {
      this.renderer.addClass(this.el.nativeElement, 'invalid-decimal');
    } else {
      this.renderer.removeClass(this.el.nativeElement, 'invalid-decimal');
    }
  }

  private formatDecimal(value: string): string {
    const parts = value.split(',');
    
    if (parts[0] === '') {
      parts[0] = '0';
    } else if (parts[0].length > 1) {
      parts[0] = parts[0].replace(/^0+/, '') || '0';
    }
    
    if (parts[1] === '') {
      parts[1] = '0';
    } else {
      parts[1] = parts[1].replace(/0+$/, '');
      
      if (parts[1] === '') {
        return parts[0]; 
      }
    }
    
    return parts.join(',');
  }
}
