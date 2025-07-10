import { bootstrapModule } from '@maggioli/sdk-commons';

import { DevCommons } from './app/app.module';
import { environment } from './environments/environment';

bootstrapModule(environment, DevCommons).subscribe({
  complete: () => { console.log('bootstrapModule::complete') },
  error: (e: Error) => { console.error('bootstrapModule::error', e) },
});
