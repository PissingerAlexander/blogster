export class GetPage<T> {
  private readonly _itemCount: number;
  private readonly _pageCount: number;
  private readonly _pageContent: T[];


  constructor(itemCount:number, pageCount: number, pageContent: T[]) {
    this._itemCount = itemCount;
    this._pageCount = pageCount;
    this._pageContent = pageContent;
  }

  get itemCount(): number {
    return this._itemCount;
  }

  get pageCount(): number {
    return this._pageCount;
  }

  get pageContent(): T[] {
    return this._pageContent;
  }
}
