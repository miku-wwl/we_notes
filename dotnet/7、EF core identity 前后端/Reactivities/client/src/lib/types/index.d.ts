type Profile = {
  id: string
  displayName: string
  bio?: string
  imageUrl?: string
  followersCount?: number
  followingCount?: number
  following?: boolean
}

type Photo = {
  id: string
  url: string
}

type User = {
  id: string
  email: string
  displayName: string
  imageUrl?: string
}