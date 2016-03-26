package aec;

import java.util.ArrayList;

class Cache
{
	Cache()
	{
		for ( int i = 0; i < INIT_CAPACITY; i++ )
		{
			items.add( new AeItem() );
		}
	}

	AeItem
	alloc()
	{
		AeItem		item = null;

		if ( items.isEmpty() == false )
		{
			item = items.remove( 0 );
		}
		else
		{
			item = new AeItem();
		}

		return item;
	}

	void
	free( AeItem item )
	{
		int		N = items.size();

		for ( int i = 0; i < N; i++ )
		{
			if ( item == items.get( i ) )
			{
				return;
			}
		}

		items.add( item );
	}


	private static final int		INIT_CAPACITY = 128;
	private ArrayList<AeItem>		items = new ArrayList<AeItem>( INIT_CAPACITY );
}
