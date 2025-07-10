import { bootstrapModule } from '@maggioli/sdk-commons';

import { DevControlsModule } from './app/app.module';
import { environment } from './environments/environment';

bootstrapModule(environment, DevControlsModule).subscribe({
  complete: () => { console.log('bootstrapModule::complete') },
  error: (e: Error) => { console.error('bootstrapModule::error', e) },
});
