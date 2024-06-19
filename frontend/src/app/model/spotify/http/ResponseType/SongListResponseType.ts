export interface SongListResponseType {
  href: string,
  items: [
    Track
  ]
  limit: number,
  next: string | null,
  offset: number,
  previous: string | null,
  total: number,
}

export interface Track {
  id: string,
  name: string,
}
