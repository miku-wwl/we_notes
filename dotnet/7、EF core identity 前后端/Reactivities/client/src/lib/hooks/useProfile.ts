import { useQuery, useQueryClient } from "@tanstack/react-query"
import agent from "../api/agent"
import { useMemo } from "react";

export const useProfile = (id?: string, predicate?: string) => {
    const queryClient = useQueryClient();

    const { data: profile, isLoading: loadingProfile } = useQuery<Profile>({
        queryKey: ['profile', id],
        queryFn: async () => {
            const response = await agent.get<Profile>(`/profiles/${id}`);
            return response.data
        },
        enabled: !!id && !predicate
    })


    const isCurrentUser = useMemo(() => {
        return id === queryClient.getQueryData<User>(['user'])?.id
    }, [id, queryClient])

    return {
        profile,
        loadingProfile,
        isCurrentUser,
    }
}

