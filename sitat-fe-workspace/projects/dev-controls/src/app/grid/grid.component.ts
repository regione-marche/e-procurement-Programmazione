import { ChangeDetectionStrategy, Component, OnInit, Type } from '@angular/core';
import { IDictionary } from '@maggioli/sdk-commons';
import {
  SdKTableCellDateViewerComponent,
  SdkDatasourceService,
  SdkTableCellActionsIconViewerComponent,
  SdkTableCellActionsViewerComponent,
  SdkTableCellCurrencyViewerComponent,
  SdkTableCellLinkViewerComponent,
  SdkTableCellNumberViewerComponent,
  SdkTableCellStringViewerComponent,
  SdkTableCellViewer,
  SdkTableConfig,
  SdkTableRowDto,
  SdkTableToolbarActionPerfomer,
} from '@maggioli/sdk-table';
import { Observable, of } from 'rxjs';

import { GridUtilsService } from './grid-utils.service';
import { GridDatasourceService } from './grid.datasource.service';

@Component({
  selector: 'grid',
  templateUrl: './grid.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GridComponent implements OnInit {

  private viewTypeMap: IDictionary<Type<SdkTableCellViewer>> = {
    SDK_GRID_VIEW_CELL_STRING: SdkTableCellStringViewerComponent,
    SDK_GRID_VIEW_CELL_NUMBER: SdkTableCellNumberViewerComponent,
    SDK_GRID_VIEW_CELL_ACTIONS: SdkTableCellActionsViewerComponent,
    SDK_GRID_VIEW_CELL_DATE: SdKTableCellDateViewerComponent,
    SDK_GRID_VIEW_CELL_CURRENCY: SdkTableCellCurrencyViewerComponent,
    SDK_GRID_VIEW_CELL_LINK: SdkTableCellLinkViewerComponent,
    SDK_GRID_VIEW_CELL_ACTIONS_ICON: SdkTableCellActionsIconViewerComponent
  };

  private datasource: GridDatasourceService;

  public config$: Observable<SdkTableConfig>;

  private config: any = {
    datasource: undefined,
    sortable: {
      allowUnsort: false,
      mode: 'single'
    },
    sort: [
      {
        field: 'id',
        dir: 'asc'
      }
    ],
    // pageable: {
    //   buttonCount: 5,
    //   info: true,
    //   pageSizes: [
    //     10,
    //     20,
    //     50,
    //     100
    //   ],
    //   type: 'numeric'
    // },
    pageable: false,
    pageSize: 20,
    messages: {
      noRecords: 'Nessun record trovato',
      clearFilters: 'Reimposta filtri',
      applyFilters: 'Applica filtri'
    },
    filterable: true,
    filter: {
      filterLabel: 'Filtri',
      activeFiltersLabel: 'Filtri attivi',
      fields: [
        {
          type: 'FORM-SECTION',
          code: 'id-form-section',
          label: 'Label form section',
          mnemonico: 'section',
          fieldSections: [
            {
              type: 'TEXTBOX',
              code: 'id-textbox1',
              label: 'Label della textbox1',
              mnemonico: 'text1',
              mappingOutput: 'numero'
            },
            {
              type: 'COMBOBOX',
              code: 'id-combobox',
              label: 'Label della combobox',
              mnemonico: 'combobox',
              itemsProvider: () => {
                return of([
                  {
                    key: '1',
                    value: 'Si'
                  },
                  {
                    key: '0',
                    value: 'No'
                  }
                ]);
              },
              mappingOutput: 'parametro2'
            }
          ]
        }
      ]
    },
    columns: [
      {
        viewer: {
          type: 'SDK_GRID_VIEW_CELL_STRING'
        },
        title: 'Numero',
        field: 'numero',
        width: 125,
        hidden: false,
        sortable: true,
        resizable: false
      }
    ]
  };

  private performers: IDictionary<SdkTableToolbarActionPerfomer>;

  constructor(private sdkDatasourceService: SdkDatasourceService, private gridUtilsService: GridUtilsService) { }

  ngOnInit() {
    this.initPerformers();
    this.datasource = this.sdkDatasourceService.create(GridDatasourceService, {});
    this.config = this.gridUtilsService.parseDescriptor(this.config, this.datasource, this.performers, 'dd/mm/yy');
    this.config$ = of(this.config);
  }

  public onOutput(item: any): void {
    console.log(item);
  }

  private initPerformers(): void {
    this.performers = {
      dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
        console.log('dettaglio dataItem >>>', selectedRow.dataItem);
      },
      delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
        console.log('delete dataItem >>>', selectedRow.dataItem);
      }
    }
  }

}
